
# 1st stage, build the app
FROM eclipse-temurin:23 AS build

WORKDIR /usr/share

# Install maven
RUN set -x && \
    curl -O https://archive.apache.org/dist/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz && \
    tar -xvf apache-maven-*-bin.tar.gz  && \
    rm apache-maven-*-bin.tar.gz && \
    mv apache-maven-* maven && \
    ln -s /usr/share/maven/bin/mvn /bin/

WORKDIR /helidon

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
ADD pom.xml .
RUN mvn package -Dmaven.test.skip -Declipselink.weave.skip

# Do the Maven build to create the custom Java Runtime Image
# Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn package -Pjlink-image -DskipTests && echo "done!"

# 2nd stage, build the final image with the JRI built in the 1st stage
FROM debian:stable-slim

LABEL org.opencontainers.image.source=https://github.com/upa-io/helidon-chassis-java
LABEL org.opencontainers.image.description="Helidon 4.0.8 Chassis MP API"
LABEL org.opencontainers.image.licences="Apache 2.0"    

# Set the timezone
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/America/Panama /etc/localtime && echo America/Panama > /etc/timezone && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Create a new user
RUN useradd -m helidon

# Create the log directory and give write permissions
RUN mkdir -p /helidon && chown -R helidon:helidon /helidon

WORKDIR /helidon

# Copy the JRI from the 1st stage and change the owner to the new user
COPY --from=build --chown=helidon:helidon /helidon/target/quickstart-mp-jri ./

# Switch to the new user
USER helidon

ENTRYPOINT ["/bin/bash", "/helidon/bin/start"]

EXPOSE 8080

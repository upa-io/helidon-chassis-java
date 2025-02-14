
package open.upaio;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

public class PrincipalResource {

    private static final Logger LOGGER = Logger.getLogger(PrincipalResource.class.getName());

    @GET
    @Path("/greet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response greet() {
        LOGGER.info("Greeting the user");
        String greeting = "Hello World!";
        return Response.ok(greeting).build();
    }
}

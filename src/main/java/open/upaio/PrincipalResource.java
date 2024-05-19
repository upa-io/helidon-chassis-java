
package open.upaio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

@Path("/open/upaio/principal/helidon/v1")
@RequestScoped
public class PrincipalResource {

    private static final Logger LOGGER = Logger.getLogger(PrincipalResource.class.getName());
    private static final Random RANDOM = new Random();

    @POST
    @Path("/memory-intensive")
    @Produces(MediaType.APPLICATION_JSON)
    public Response memoryIntensive() {
        // Generate a large amount of data
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            data.add("Item " + i);
        }

        LOGGER.info("Memory-intensive operation completed");

        return Response.ok("Memory-intensive operation completed. Data size: " + data.size()).build();
    }

    @GET
    @Path("/work-cpu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response workCpu() {
        // Perform CPU-intensive task here
        // For example, calculate Fibonacci sequence
        int n = 40;
        int result = fibonacci(n);

        LOGGER.info("CPU work completed");

        // Introduce an artificial delay
        try {
            // Random delay between 500ms and 2000ms
            long delay = 500L + RANDOM.nextInt(1000);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.SEVERE, "Thread was interrupted", e);
        }

        return Response.ok("CPU work completed. Result: " + result).build();
    }

    @GET
    @Path("/fast-response")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fastResponse() {
        // Perform a quick task here
        // For example, a simple calculation
        int result = 2 * 2;

        LOGGER.info("Fast response completed");

        // Introduce an artificial delay
        try {
            // Random delay between 10ms and 100ms
            long delay = 10L + RANDOM.nextInt(90);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.SEVERE, "Thread was interrupted", e);
        }

        return Response.ok("Fast response completed. Result: " + result).build();
    }

    private int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

}

package florent.lb.demo.craft.infra;

import florent.lb.demo.craft.domain.Drink;
import florent.lb.demo.craft.service.BarService;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/api/bar/")
public class BarRessource {

    private final BarService barService;

    public BarRessource(BarService barService) {
        this.barService = barService;
    }

    @POST
    @Path("{drink}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Drink> commandDrink(@PathParam("drink") String drink) {
        try {
            return barService.command(drink);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("sendFinancialReport")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> commandDrink() {
        return barService.sendFinancialReport();

    }
}
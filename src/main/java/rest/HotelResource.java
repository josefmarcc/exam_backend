package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.HotelFacade;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import utils.EMF_Creator;
import utils.DataFetcher;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
@Path("hotels")
public class HotelResource {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HotelFacade hf = HotelFacade.getHotelFacade(EMF);

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allHotels() throws IOException, InterruptedException {
        return DataFetcher.fetchFromHotelsApi();
    }

}

package rest;

import DTO.HotelDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import entities.Booking;
import entities.User;
import facades.BookingFacade;
import facades.HotelFacade;
import facades.UserFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import security.errorhandling.AuthenticationException;

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
    private static final UserFacade uf = UserFacade.getUserFacade(EMF);
    private static final BookingFacade bf = BookingFacade.getBookingFacade(EMF);

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allHotels() throws IOException, InterruptedException {
        return DataFetcher.fetchFromHotelsApi();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getHotel(@PathParam("id") int id) throws IOException, InterruptedException, ParseException {
        HotelDTO hotelDTO = DataFetcher.fetchSingleHotel(id);
        return gson.toJson(hotelDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addBooking(String bookingGson, String userName) {

        try {
            User user = uf.getUser(userName);
            Booking booking = gson.fromJson(bookingGson, Booking.class);
            bf.makeBooking(user.getUserName(), booking.getHotel(), booking.getDays(), booking.getPrice());
            return "Book Added";
        } catch (AuthenticationException ex) {
            Logger.getLogger(HotelResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Book Added";
    }

}

package rest;

import DTO.BookingDTO;
import DTO.HotelDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import errorhandling.NotFoundException;
import facades.BookingFacade;
import facades.HotelFacade;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
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
    public String addBooking(String booking) throws NotFoundException {
        BookingDTO bookingDTO = gson.fromJson(booking, BookingDTO.class);
        bf.addBooking(bookingDTO.getStartDate(), bookingDTO.getDays(), bookingDTO.getPrice(), bookingDTO.getHotel(), bookingDTO.getUserName());
        return gson.toJson(bookingDTO);
    }

}

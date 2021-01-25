/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.BookingDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.BookingNotFoundException;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.BookingFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
@Path("booking")
public class BookingResource {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BookingFacade bf = BookingFacade.getBookingFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("user")
    public String addBooking(String booking) throws NotFoundException {
        String thisuser = securityContext.getUserPrincipal().getName();
        BookingDTO bookingDTO = gson.fromJson(booking, BookingDTO.class);
        bf.addBooking(bookingDTO.getStartDate(), bookingDTO.getDays(), bookingDTO.getPrice(), bookingDTO.getHotel(), bookingDTO.getUserName());
        gson.toJson(bookingDTO);
        return "Added booking to" + thisuser;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{userName}")
    public String getUserBookings(@PathParam("userName") String userName) {
        List<BookingDTO> bookingList = bf.getUserBookings(userName);
        return gson.toJson(bookingList);
    }

    @PUT
    @Path("/update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateBooking(@PathParam("id") int id, String booking) throws BookingNotFoundException, MissingInputException {
        BookingDTO bDTO = gson.fromJson(booking, BookingDTO.class);
        bDTO.setId(id);
        BookingDTO bNew = bf.editBooking(bDTO);
        return gson.toJson(bNew);
    }

}

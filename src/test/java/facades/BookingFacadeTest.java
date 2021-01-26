/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Booking;
import entities.Role;
import entities.User;
import errorhandling.AlreadyExsistException;
import errorhandling.NotFoundException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class BookingFacadeTest {

    private static EntityManagerFactory emf;
    private static BookingFacade facade;
    private User user1, user2;
    private Role userRole, adminRole;
    private Booking booking1, booking2;

    public BookingFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BookingFacade.getBookingFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("user", "kode123");
        user2 = new User("admin", "kodetest");
        userRole = new Role("user");
        adminRole = new Role("admin");
        booking1 = new Booking(new Date(), 5, (float) 1.5, "Blue Hotel");
        booking2 = new Booking(new Date(), 2, (float) 2, "Green Hotel");

        // HUSK DELETE BOOKING FØRST
        try {
            em.getTransaction().begin();
            em.createQuery("Delete from Booking").executeUpdate();
            em.createQuery("Delete from User").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();
            user1.addRole(userRole);
            user2.addRole(adminRole);
            user1.addBooking(booking1);
            user1.addBooking(booking2);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user1);
            em.persist(user2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(2, facade.getUserBookings(user1.getUserName()).size(), "Expects two rows in the database");
    }

    /**
     * Test of AddBooking method, of class BookingFacade.
     *
     * @throws errorhandling.AlreadyExsistException
     * @throws errorhandling.NotFoundException
     */
    //@Disabled
    @Test
    public void testAddBooking() throws AlreadyExsistException, NotFoundException {
        System.out.println("TESTING SIZE AFTER ADD METHOD ....");
        String userName = "user";

        facade.addBooking(new Date(), 2, (float) 1.5, "Hotel", userName);
        System.out.println("TESTING SIZE AFTER ADD METHOD");
        assertEquals(3, facade.getUserBookings(userName).size(), "Expects one row as there is 1 added booking");
    }
}

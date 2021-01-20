package facades;

import DTO.BookingDTO;
import entities.Booking;
import entities.User;
import errorhandling.NotFoundException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class BookingFacade {

    private static BookingFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BookingFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BookingFacade getBookingFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookingFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BookingDTO addBooking(Date startDate, int days, float price, String hotel, String userName) throws NotFoundException {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userName);

        if (user == null) {
            throw new NotFoundException("User could not be found");
        }

        try {
            em.getTransaction().begin();
            Booking booking = new Booking(startDate, days, price, hotel);
            booking.setUser(user);
            em.persist(booking);
            em.getTransaction().commit();
            return new BookingDTO(booking);
        } finally {
            em.close();
        }

    }

}

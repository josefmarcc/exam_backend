package facades;

import entities.Booking;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

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

    public void makeBooking(String userName, String hotel, int days, int price) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();

        User user;

        try {
            em.getTransaction().begin();
            user = UserFacade.getUserFacade(emf).getUser(userName);
            Booking userBooking = new Booking(hotel, days, price);
            user.addBooking(userBooking);
            em.persist(userBooking);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws AuthenticationException {
        emf = EMF_Creator.createEntityManagerFactory();
        BookingFacade facade = BookingFacade.getBookingFacade(emf);
        EntityManager em = emf.createEntityManager();

        facade.makeBooking("user", "hotel", 2, 1);

    }

}

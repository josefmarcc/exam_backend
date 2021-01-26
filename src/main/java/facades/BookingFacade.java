package facades;

import DTO.BookingDTO;
import entities.Booking;
import entities.User;
import errorhandling.BookingNotFoundException;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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

    public List<BookingDTO> getUserBookings(String userName) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery<Booking> q1
                    = em.createQuery("SELECT b FROM Booking b WHERE b.user.userName LIKE :name", Booking.class);
            List<Booking> bookingList = q1.setParameter("name", userName).getResultList();

            List<BookingDTO> bookingListDTO = new ArrayList();
            for (Booking booking : bookingList) {
                BookingDTO bookingDTO = new BookingDTO(booking);
                bookingListDTO.add(bookingDTO);
            }

            return bookingListDTO;
        } finally {
            em.close();
        }
    }

    public BookingDTO editBooking(BookingDTO b) throws BookingNotFoundException, MissingInputException {
        if ((b.getHotel().length() == 0)) {
            throw new MissingInputException("Hotel input missing");
        }
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            Booking booking = em.find(Booking.class, b.getId());
            if (booking == null) {
                throw new BookingNotFoundException(String.format("Booking with id: (%d) not found", b.getId()));
            } else {
                booking.setDays(b.getDays());
                booking.setStartDate(b.getStartDate());
                booking.setPrice(b.getPrice());
            }
            em.getTransaction().commit();
            return new BookingDTO(booking);
        } finally {
            em.close();
        }
    }

}

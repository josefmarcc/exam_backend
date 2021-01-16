package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class HotelFacade {

    private static HotelFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HotelFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HotelFacade getHotelFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HotelFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}

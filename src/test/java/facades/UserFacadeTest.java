/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Booking;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private User user1, user2;
    private Role userRole, adminRole;
    private Booking booking;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        booking = new Booking("Hotel", 1, 2);
        user1 = new User("user", "kode123");
        user2 = new User("admin", "kodetest");
        userRole = new Role("user");
        adminRole = new Role("admin");

        try {
            em.getTransaction().begin();
            em.createQuery("Delete from User").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();
            em.createQuery("DELETE from Booking").executeUpdate();
            user1.addRole(userRole);
            user1.addBooking(booking);
            user2.addRole(adminRole);
            user2.addBooking(booking);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(booking);
            em.persist(user1);
            em.persist(user2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(2, facade.getAllUsers().size(), "Expects two rows in the database");
    }

    /**
     * Test of addUser method, of class UserFacade.
     */
    //@Disabled
    @Test
    public void testAddUser() {
        System.out.println("TESTING SIZE AFTER ADD METHOD ....");

        String userName = "user3";
        String userPassword = "thisIsASecretPassWord1";

        facade.addUser(userName, userPassword);
        System.out.println("TESTING SIZE AFTER ADD METHOD");
        assertEquals(3, facade.getAllUsers().size(), "Expects three rows in the database");
    }

}

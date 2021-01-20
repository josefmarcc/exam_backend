package DTO;

import entities.Booking;
import java.util.Date;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class BookingDTO {

    private Date startDate;
    private int days;
    private float price;
    private String hotel;
    private String userName;

    public BookingDTO(Booking booking) {
        this.startDate = booking.getStartDate();
        this.days = booking.getDays();
        this.price = booking.getPrice();
        this.hotel = booking.getHotel();
        this.userName = booking.getUser().getUserName();
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getDays() {
        return days;
    }

    public float getPrice() {
        return price;
    }

    public String getHotel() {
        return hotel;
    }

    public String getUserName() {
        return userName;
    }
}

package uit.ie303.demo.bookingdetails;

import java.util.List;

import jakarta.persistence.*;
import uit.ie303.demo.booking.Booking;
import uit.ie303.demo.rooms.Rooms;

@Entity
@Table(name = "booking_details")
public class BookingDetails {
    @Id
    private Long booking_detail_id;
    // private int booking_id;
    // private int room_id;

    public BookingDetails(){}

    // rooms 1 -n booking details
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Rooms room;

    // booking details 1 - 1 booking
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // Getter & Setter

    public Long getBooking_detail_id() {
        return booking_detail_id;
    }

    public void setBooking_detail_id(Long booking_detail_id) {
        this.booking_detail_id = booking_detail_id;
    }

    // public int getBooking_id() {
    // return this.booking_id;
    // }

    // public void setBooking_id(int booking_id) {
    // this.booking_id = booking_id;
    // }

    // public int getRoom_id() {
    // return room_id;
    // }

    // public void setRoom_id(int room_id) {
    // this.room_id = room_id;
    // }

}

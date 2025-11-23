package uit.ie303.demo.bookingdetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import uit.ie303.demo.booking.Booking;
import uit.ie303.demo.rooms.Rooms;

@Entity
@Table(name = "booking_details")
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_detail_id")
    @JsonProperty("booking_detail_id")
    private Long bookingDetailId;

    // @Column(name = "booking_id", nullable = false)
    // @JsonProperty("booking_id")
    // private Integer bookingId;

    // @Column(name = "room_id", nullable = false)
    // @JsonProperty("room_id")
    // private Integer roomId;

    // rooms n - 1 booking details
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonManagedReference
    private Rooms room;

    // booking details 1 - 1 booking
    @OneToOne
    @MapsId // use the same ID as booking
    @JsonManagedReference
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // --- Getters and Setters ---
    public Long getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(Long bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    // public Integer getBookingId() {
    //     return bookingId;
    // }

    // public void setBookingId(Integer bookingId) {
    //     this.bookingId = bookingId;
    // }

    // public Integer getRoomId() {
    //     return roomId;
    // }

    // public void setRoomId(Integer roomId) {
    //     this.roomId = roomId;
    // }
}
package uit.ie303.demo.rooms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import uit.ie303.demo.bookingdetails.BookingDetails;
import uit.ie303.demo.roomtype.RoomType;

@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    private Long room_id;
    private int room_number;
    // private int type_id;
    private String amenities;
    private String room_view;
    private String room_status;

    // room type 1 - n rooms
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    @JsonBackReference
    private RoomType roomtype;

    // rooms 1 - n booking details
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<BookingDetails> booking_details;



    // Getter & Setter
    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    // public int getType_id() {
    //     return type_id;
    // }

    // public void setType_id(int type_id) {
    //     this.type_id = type_id;
    // }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getRoom_view() {
        return room_view;
    }

    public void setRoom_view(String room_view) {
        this.room_view = room_view;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }
}

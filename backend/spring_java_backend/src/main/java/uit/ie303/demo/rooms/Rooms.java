package uit.ie303.demo.rooms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import uit.ie303.demo.bookingdetails.BookingDetails;
import uit.ie303.demo.roomtype.RoomType;

@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    private Long room_id;

    @Column(name = "room_number")
    private int roomNumber;
    
    // private int type_id;
    private String amenities;
    private String room_view;
    private String room_status;

    // room type 1 - n rooms
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private RoomType roomType;

    // rooms 1 - n booking details
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<BookingDetails> booking_details;


    // get type_id solution 1
    @Column(name = "type_id", insertable = false, updatable = false)
    @JsonProperty("type_id")
    private Integer typeId;

    //get type_id solution 2
    // @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "typeId")





    // Getter & Setter
    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public int getRoom_number() {
        return roomNumber;
    }

    public void setRoom_number(int room_number) {
        this.roomNumber = room_number;
    }

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

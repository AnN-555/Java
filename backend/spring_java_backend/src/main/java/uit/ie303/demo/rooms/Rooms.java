package uit.ie303.demo.rooms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import uit.ie303.demo.roomtype.RoomType;

@Entity
@Table(name = "rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @Column(name = "room_number", unique = true, nullable = false)
    private Integer roomNumber;

    private String amenities;
    private String room_view;
    private String room_status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    @JsonIgnore // hide full object
    private RoomType roomType;

    public Rooms() {

    }

    @JsonProperty("type_id")
    public Long getTypeId() {
        return roomType != null ? roomType.getId() : null;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Long getRoom_id() {
        return this.room_id;
    }

    public Integer getRoom_number() {
        return this.roomNumber;
    }

    // public Long getType_id() {
    // return this.type_id;
    // }

    public String getAmenities() {
        return this.amenities;
    }

    public String getRoom_status() {
        return this.room_status;
    }

    public String getRoom_view() {
        return this.room_view;
    }

    // public void setType_id(Long id){
    // this.type_id = id;
    // }

    public void setAmenities(String item) {
        this.amenities = item;
    }

    public void setRoom_id(Long id) {
        this.room_id = id;
    }

    public void setRoom_number(Integer n) {
        this.roomNumber = n;
    }

    public void setRoom_status(String stt) {
        this.room_status = stt;
    }

    public void setRoom_view(String stt) {
        this.room_view = stt;
    }

}

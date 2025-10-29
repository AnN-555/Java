package uit.ie303.demo.roomtype;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import uit.ie303.demo.rooms.Rooms;

@Entity
@Table(name = "roomtype")
public class RoomType {

    @Id
    private Long type_id;

    private String type_name;
    private double price;
    private int capacity;
    private String type_description;
    private double area;
    private String image_url;

    // roomtype 1 - n rooms
    @OneToMany(mappedBy = "roomtype", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Rooms> rooms;

    // Getter and Setter
    public Long getId() {
        return this.type_id;
    }

    public void setId(Long id) {
        this.type_id = id;
    }

    public String getTypeName() {
        return this.type_name;
    }

    public void setTypeName(String name) {
        this.type_name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double pr) {
        this.price = pr;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int cp) {
        this.capacity = cp;
    }

    public String getDescription() {
        return this.type_description;
    }

    public void setDescription(String ds) {
        this.type_description = ds;
    }

    public double getArea() {
        return this.area;
    }

    public void setArea(double ar) {
        this.area = ar;
    }

    public String getImgSrc() {
        return this.image_url;
    }

    public void setImgSrc(String url) {
        this.image_url = url;
    }

}

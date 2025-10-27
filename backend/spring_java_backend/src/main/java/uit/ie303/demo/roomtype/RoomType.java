package uit.ie303.demo.roomtype;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity
@Table(name = "roomtype")
public class RoomType {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type_name;
    private double price;
    private int capacity;
    private String description;
    private double area;
    private String img_url;

    // Getter and Setter
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    


}

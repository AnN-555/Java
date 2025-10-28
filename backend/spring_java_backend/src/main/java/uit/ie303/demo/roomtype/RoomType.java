package uit.ie303.demo.roomtype;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity
@Table(name = "roomtype")
public class RoomType {

    @Id
    private Long type_id;

    private String type_name;
    private double price;
    private int capacity;
    private String description;
    private double area;
    private String img_url;

    // Getter and Setter
    public Long getId(){
        return this.type_id;
    }

    public void setId(Long id){
        this.type_id = id;
    }

    public String getTypeName(){return this.type_name;}

    public void setTypeName(String name){this.type_name = name;}

    public double getPrice(){return this.price;}
    public void setPrice(double pr){this.price = pr;}
    public int getCapacity(){return this.capacity;}
    public void setCapacity(int cp){this.capacity = cp;}
    public String getDescription(){return this.description;}
    public void setDescription(String ds){this.description = ds;}
    public double getArea(){return this.area;}
    public void setArea(double ar){this.area = ar;}
    public String getImgSrc(){return this.img_url;}
    public void setImgSrc(String url){this.img_url = url;}
    


}

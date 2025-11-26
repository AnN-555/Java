package uit.ie303.demo.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "roomtype")
public class RoomType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "type_id")
  private Long id;

  @Column(name = "type_name", nullable = false)
  private String typeName;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  @Column(name = "type_description")
  private String description;

  @Column(name = "area", nullable = false)
  private Float area;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  /* getter and setter */
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getArea() {
    return area;
  }

  public void setArea(Float area) {
    this.area = area;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}

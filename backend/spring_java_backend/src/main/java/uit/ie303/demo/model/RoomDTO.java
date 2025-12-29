package uit.ie303.demo.model;

import java.time.LocalDate;

public class RoomDTO {
    private String typeName;
    private int price;
    private String roomView;
    private String image;
    private LocalDate checkIn;   
    private LocalDate checkOut;
    
    public RoomDTO(String typeName, int price, String roomView,String image, LocalDate checkIn, LocalDate checkOut) {
        this.typeName = typeName;
        this.price = price;
        this.roomView = roomView;
        this.image = image;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Getters
    public String getTypeName() { return typeName; }
    public int getPrice() { return price; }
    public String getRoomView() { return roomView; }
    public String getImage() { return image; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    public RoomDTO(String typeName, int price, String roomView,String image) {
        this(typeName, price, roomView, image, null, null);
    }
}


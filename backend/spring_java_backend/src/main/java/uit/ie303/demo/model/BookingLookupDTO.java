package uit.ie303.demo.model;

import java.time.LocalDate;

public class BookingLookupDTO {

    private String id;
    private String fullName;
    private String email;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int nights;
    private double totalAmount;
    private String roomType;
    private String roomImage;

    public BookingLookupDTO() {
    }

    public BookingLookupDTO(String id, String fullName, String email, LocalDate checkIn,
            LocalDate checkOut, int nights, double totalAmount,
            String roomType, String roomImage) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.roomType = roomType;
        this.roomImage = roomImage;
    }

    //getter/setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }
}

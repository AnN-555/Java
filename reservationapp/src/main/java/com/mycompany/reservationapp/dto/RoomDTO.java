package com.mycompany.reservationapp.dto;

import java.time.LocalDate;

public class RoomDTO {
    private String typeName;
    private int price;
    private String roomView;
    private LocalDate checkIn;   // thêm trường check-in
    private LocalDate checkOut;  // thêm trường check-out

    // Constructor đầy đủ
    public RoomDTO(String typeName, int price, String roomView, LocalDate checkIn, LocalDate checkOut) {
        this.typeName = typeName;
        this.price = price;
        this.roomView = roomView;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Getters
    public String getTypeName() { return typeName; }
    public int getPrice() { return price; }
    public String getRoomView() { return roomView; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    public RoomDTO(String typeName, int price, String roomView) {
        this(typeName, price, roomView, null, null);
    }
}


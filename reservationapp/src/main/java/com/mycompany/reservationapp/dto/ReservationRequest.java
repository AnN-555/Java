package com.mycompany.reservationapp.dto;

public class ReservationRequest {

    private String checkIn;
    private String checkOut;
    private String rooms;
    private int adults;
    private int children;

    // ==== GETTERS ====
    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getRooms() {
        return rooms;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    // ==== SETTERS ====
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", rooms='" + rooms + '\'' +
                ", adults=" + adults +
                ", children=" + children +
                '}';
    }
}

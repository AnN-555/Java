package uit.ie303.demo.model;

public class ReservationDTO {
    private String bookingId;       // ex BKG-1764327708034-253
    private String checkIn;         // dd/MM/yyyy
    private String checkOut;
    private String totalGuests;
    private double totalPrice;
    private String customerName;
    private String email;           // check exists later
    private String phone;           // check exists later
    private String address;
    private String roomName;
    private String roomView;        // no frontend for this yet, consider for upgrading
    private int roomsSelected;
    private String request;         // null

    public ReservationDTO() {}

    // getter/setter
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

    public String getTotalGuests() { return totalGuests; }
    public void setTotalGuests(String totalGuests) { this.totalGuests = totalGuests; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getRoomView() { return roomView; }
    public void setRoomView(String roomView) { this.roomView = roomView; }

    public int getRoomsSelected() { return roomsSelected; }
    public void setRoomsSelected(int roomsSelected) { this.roomsSelected = roomsSelected; }

    public String getRequest() { return request; }
    public void setRequest(String request) { this.request = request; }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "bookingId='" + bookingId + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", totalGuests='" + totalGuests + '\'' +
                ", totalPrice=" + totalPrice +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomView='" + roomView + '\'' +
                ", roomsSelected=" + roomsSelected +
                ", request='" + request + '\'' +
                '}';
    }
}
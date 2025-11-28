package uit.ie303.demo.model;

public class ReservationDTO {
    private String bookingId;       // từ frontend, ví dụ BKG-1764327708034-253 (chỉ để hiển thị, không lưu DB)
    private String checkIn;         // dd/MM/yyyy
    private String checkOut;
    private String totalGuests;     // ví dụ: "3 Room(s) - 1 Guests"
    private double totalPrice;
    private String customerName;
    private String email;
    private String phone;           // THÊM DÒNG NÀY (rất quan trọng)
    private String address;
    private String roomName;        // ví dụ: Deluxe Twin Room
    private String roomView;        // ví dụ: City View
    private int roomsSelected;
    private String request;         // special request, có thể null (THÊM DÒNG NÀY)

    // Constructor mặc định
    public ReservationDTO() {}

    // === GETTER & SETTER ===
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

    // THÊM 2 CÁI NÀY
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

    // THÊM CÁI NÀY
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
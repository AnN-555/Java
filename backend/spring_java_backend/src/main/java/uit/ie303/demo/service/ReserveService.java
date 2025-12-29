package uit.ie303.demo.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import uit.ie303.demo.model.ReservationDTO;

@Service
public class ReserveService {

    private final CustomerService customerService;
    private final RoomAvailableService roomAvailableService;
    private final BookingService bookingService;
    private final PaymentService paymentService;

    public ReserveService(CustomerService customerService,
                              RoomAvailableService roomAvailableService,
                              BookingService bookingService,
                              PaymentService paymentService) {
        this.customerService = customerService;
        this.roomAvailableService = roomAvailableService;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }


    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=diamondhotel;encrypt=false;trustServerCertificate=true;";
    private static final String DB_USER = "diamondhotel";
    private static final String DB_PASS = "diamond";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String saveReservationToDb(ReservationDTO dto) throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            conn.setAutoCommit(false);

            String bookingId = dto.getBookingId();
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Booking ID từ frontend không được để trống!");
            }

            // 1. Customer
            Integer customerId = customerService.findExistingCustomer(conn, dto.getEmail(), dto.getPhone());
            if (customerId == null) {
                customerId = customerService.insertCustomer(conn, dto);
            }

            // 2. Dates
            LocalDate checkInDate = LocalDate.parse(dto.getCheckIn(), DTF);
            LocalDate checkOutDate = LocalDate.parse(dto.getCheckOut(), DTF);
            Timestamp checkInTs = Timestamp.valueOf(checkInDate.atStartOfDay());
            Timestamp checkOutTs = Timestamp.valueOf(checkOutDate.atStartOfDay());

            // 3. Rooms
            List<Integer> availableRoomIds = roomAvailableService.findAvailableRooms(conn,
                    dto.getRoomName(), dto.getRoomView(), dto.getRoomsSelected(), checkInTs, checkOutTs);

            if (availableRoomIds.size() < dto.getRoomsSelected()) {
                throw new IllegalStateException("Không đủ phòng trống! Chỉ còn " + availableRoomIds.size() +
                        " phòng " + dto.getRoomName() + " - " + dto.getRoomView());
            }

            List<Integer> roomsToBook = availableRoomIds.subList(0, dto.getRoomsSelected());

            // 4. Booking
            bookingService.insertBooking(conn, bookingId, customerId, checkInTs, checkOutTs, dto);
            bookingService.insertBookingDetails(conn, bookingId, roomsToBook);

            // 5. Payment
            paymentService.insertPayment(conn, bookingId, dto.getTotalPrice());

            conn.commit();
            return bookingId;
        }
    }
}

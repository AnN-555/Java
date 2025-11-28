package uit.ie303.demo.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.model.ReservationDTO;
import uit.ie303.demo.service.ReservationService;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*")
@Service
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO reservation) {
        System.out.println("Received reservation: " + reservation);

        // Bắt buộc phải có bookingId từ frontend (bạn đã sinh rồi mà)
        if (reservation.getBookingId() == null || reservation.getBookingId().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("error", "Mã đặt phòng không được để trống!"));
        }

        try {
            // Gọi service → trả về chính bookingId từ frontend (String) nếu thành công
            String savedBookingId = reservationService.saveReservationToDb(reservation);

            // Trả về cho frontend mã đẹp + thông báo thành công
            return ResponseEntity.ok(new BookingResponse(
                "success",
                "Đặt phòng thành công! Vui lòng kiểm tra email để xem thông tin chi tiết.",
                savedBookingId
            ));

        } catch (IllegalStateException e) {
            // Không đủ phòng trống
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("error", e.getMessage()));

        } catch (SQLException e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("error", "Lỗi cơ sở dữ liệu: " + e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("error", "Đặt phòng thất bại: " + e.getMessage()));
        }
    }
}

// Record trả về cho frontend (gọn nhẹ, đẹp JSON)
record BookingResponse(String status, String message, String bookingId) {}
record ErrorResponse(String status, String message) {}
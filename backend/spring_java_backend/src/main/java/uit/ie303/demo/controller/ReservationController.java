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
        if (reservation.getBookingId() == null || reservation.getBookingId().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("error", "Mã đặt phòng không được để trống!"));
        }

        try {

            String savedBookingId = reservationService.saveReservationToDb(reservation);

            return ResponseEntity.ok(new BookingResponse(
                "success",
                "Đặt phòng thành công! Vui lòng kiểm tra email để xem thông tin chi tiết.",
                savedBookingId
            ));

        } catch (IllegalStateException e) {
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

record BookingResponse(String status, String message, String bookingId) {}
record ErrorResponse(String status, String message) {}
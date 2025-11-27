package com.mycompany.reservationapp;

import com.mycompany.reservationapp.dto.InformationCustomer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.reservationapp.dto.ReservationRequest;
import com.mycompany.reservationapp.dto.RoomDTO;
import com.mycompany.reservationapp.service.RoomService;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*") // Cho phép gọi API từ Live Server hoặc port khác
public class TestController {

    // ============================
    // 1) API SEARCH RESERVATION
    // ============================
    @PostMapping("/search")
    public ResponseEntity<?> searchReservation(@RequestBody ReservationRequest request) {

        // Log dữ liệu nhận được từ frontend
        System.out.println("=== RECEIVED RESERVATION REQUEST ===");
        System.out.println("Check-in  : " + request.getCheckIn());
        System.out.println("Check-out : " + request.getCheckOut());
        System.out.println("Rooms     : " + request.getRooms());
        System.out.println("Adults    : " + request.getAdults());
        System.out.println("Children  : " + request.getChildren());
        System.out.println("====================================");

        LocalDate checkIn = LocalDate.parse(request.getCheckIn());
        LocalDate checkOut = LocalDate.parse(request.getCheckOut());

        RoomService service = new RoomService();
        List<RoomDTO> availableRooms = service.getAvailableRooms(checkIn, checkOut);

        return ResponseEntity.ok(availableRooms);

       
}

    // ============================
    // 2) API LẤY DANH SÁCH PHÒNG
    // ============================
    @GetMapping("/rooms")
    public ResponseEntity<?> getAvailableRooms() {
        RoomService service = new RoomService();
        return ResponseEntity.ok(service.getAvailableRooms());
    }




    @PostMapping("/InformationCustomer")
    public ResponseEntity<?> createInformationCustomer(@RequestBody InformationCustomer temp) {
        System.out.println("=== RECEIVED TEMP RESERVATION ===");
        System.out.println("Room ID   : " + temp.getRoomId());
        System.out.println("Check-in  : " + temp.getCheckIn());
        System.out.println("Check-out : " + temp.getCheckOut());
        System.out.println("Customer  : " + temp.getCustomer().getFirstName() + " " + temp.getCustomer().getLastName());
        System.out.println("Phone     : " + temp.getCustomer().getPhone());
        System.out.println("Email     : " + temp.getCustomer().getEmail());
        System.out.println("Country   : " + temp.getCustomer().getCountry());

        // Lưu tạm, tạo tempId
        String tempId = "TEMP" + System.currentTimeMillis();

        return ResponseEntity.ok(Map.of("tempId", tempId));
    }
}

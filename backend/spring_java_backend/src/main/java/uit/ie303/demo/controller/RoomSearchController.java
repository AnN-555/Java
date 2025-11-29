package uit.ie303.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.model.ReservationRequest;
import uit.ie303.demo.model.RoomDTO;
import uit.ie303.demo.service.RoomService;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*")
public class RoomSearchController {

    @PostMapping("/search")
    public ResponseEntity<?> searchReservation(@RequestBody ReservationRequest request) {

        // Debug log
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

    @GetMapping("/rooms")
    public ResponseEntity<?> getAvailableRooms() {
        RoomService service = new RoomService();
        return ResponseEntity.ok(service.getAvailableRooms());
    }
}

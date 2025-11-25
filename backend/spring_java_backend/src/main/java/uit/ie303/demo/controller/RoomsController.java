package uit.ie303.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uit.ie303.demo.model.RoomType;
import uit.ie303.demo.model.Rooms;
import uit.ie303.demo.service.RoomTypeService;
import uit.ie303.demo.service.RoomsService;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    @Autowired
    private RoomsService service;

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<Rooms> create(@RequestBody Rooms item) {

        Optional<RoomType> mRoomType = roomTypeService.findById(item.getId());
        item.setRoomType(mRoomType.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item));
    }

    @GetMapping
    public ResponseEntity<List<Rooms>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rooms> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Rooms> update(@RequestBody Rooms item) {
        return service.findById(item.getId())
                .map(existing -> {
                    existing.setAmenities(item.getAmenities());
                    existing.setRoomNumber(item.getRoomNumber());
                    existing.setRoomStatus(item.getRoomStatus());
                    existing.setRoomView(item.getRoomView());

                    Optional<RoomType> mRoomType = roomTypeService.findById(item.getId());
                    existing.setRoomType(mRoomType.get());
                    
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

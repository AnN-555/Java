package uit.ie303.demo.roomtype;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roomtype")
public class RoomTypeController {
    private final RoomTypeService service;

    public RoomTypeController(RoomTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<RoomType> getAllRoomType() {
        return service.getAllRoomType();
    }

    @PostMapping
    public RoomType createRoomType(@RequestBody RoomType roomType) {
        return service.createRoomType(roomType);
    }

    @GetMapping("/{id}")
    public Optional<RoomType> getRoomTypeById(Long id) {
        return service.getRoomTypeById(id);
    }

    @PutMapping("/{id}")
    public RoomType updateRoomType(@PathVariable Long id, @RequestBody RoomType tRoomType) {
        return service.updateRoomType(id, tRoomType);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomType(@PathVariable Long id) {
        service.deleteRoomType(id);
    }
}

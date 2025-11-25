package uit.ie303.demo.rooms;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomsService service;

    public RoomsController(RoomsService service) {
        this.service = service;

    }

    @GetMapping
    public List<Rooms> getAllRooms() {
        return service.getAllRooms();
    }

    @PostMapping
    public Rooms createRooms(@RequestBody Rooms item) {
        if(null == item) return null;
        return service.createRooms(item);
    }

    @GetMapping("/{id}")
    public Optional<Rooms> getRoomsById(Long id){

        return service.getRoomsByid(id);
    }
    

    @DeleteMapping("/{id}")
    public void deleteRooms(@PathVariable Long id) {
        service.deleteRoom(id);
    }

    @PutMapping("/{id}")
    public Rooms updateRoomsById(@RequestBody Rooms rooms) {
        return service.updateRooms(rooms);
    }

    @PutMapping
    public Rooms updateRooms(@RequestBody Rooms rooms) {
        return service.updateRooms(rooms);
    }
}

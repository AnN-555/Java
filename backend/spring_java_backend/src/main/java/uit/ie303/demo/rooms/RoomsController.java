package uit.ie303.demo.rooms;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = { "http://localhost:9090", "null" }) // allow access locally
@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomsRepository repository;

    public RoomsController(RoomsRepository repo) {
        this.repository = repo;

    }

    @GetMapping
    public List<Rooms> getAllRooms() {
        return repository.findAll();
    }

    @PostMapping
    public Rooms createRooms(@RequestBody Rooms item) {
        // if (repository.existsById(item.getRoom_id())) {
        //     throw new RuntimeException("Payment ID already exists");
        // }
        if(null == item) return null;
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rooms> getRoomsById(@PathVariable Long id){
        Rooms rooms = repository.findById(id).orElseThrow(()->new RuntimeException("Room not found"));
        return ResponseEntity.ok(rooms);
    }

    @DeleteMapping("/{id}")
    public void deleteRooms(Long id){
        if(null == id ) return;
        this.repository.deleteById(id);
    }
}

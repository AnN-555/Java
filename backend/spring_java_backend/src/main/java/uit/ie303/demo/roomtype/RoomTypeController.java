package uit.ie303.demo.roomtype;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally
@RestController
@RequestMapping("/api/roomtype")
public class RoomTypeController {
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeController(RoomTypeRepository repository) {
        this.roomTypeRepository = repository;
    }

    @GetMapping
    public List<RoomType> getAllRoomType() {
        return roomTypeRepository.findAll();
    }

    @PostMapping
    public RoomType createRoomType(@RequestBody RoomType roomType) {
        if (roomTypeRepository.existsById(roomType.getId())) {
            throw new RuntimeException("RoomType ID already exists");
        }
        return roomTypeRepository.save(roomType);
    }

}

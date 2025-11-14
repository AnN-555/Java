package uit.ie303.demo.rooms;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RoomsService {
    private final RoomsRepository repository;

    public RoomsService(RoomsRepository repository) {
        this.repository = repository;
    }

    public List<Rooms> getAllRooms() {
        return this.repository.findAll();
    }

    public Optional<Rooms> getRoomsByid(Long id) {
        if(null == id) return null;
        return this.repository.findById(id);
    }

    public Rooms createRooms(Rooms rooms) {
        if (this.repository.existsByRoomNumber(rooms.getRoom_number())) {
            throw new IllegalArgumentException("Room number already exists");
        }
        return this.repository.save(rooms);
    }

    public Rooms updateRooms(Long id, Rooms rooms){
        if(null == id) return null;

        Rooms room = this.repository.findById(id).orElse(null);

        if(null != room){
            // room.setRoom_id(rooms.getRoom_id()  );
            room.setAmenities(rooms.getAmenities());
            room.setRoom_number(rooms.getRoom_number());
            room.setRoom_status(rooms.getRoom_status());
            room.setRoom_view(rooms.getRoom_view());

            return this.repository.save(room);
        }

        return null;
    }

    public void deleteRoom(Long id){
        if(null == id) return;
        repository.deleteById(id);
    }

}

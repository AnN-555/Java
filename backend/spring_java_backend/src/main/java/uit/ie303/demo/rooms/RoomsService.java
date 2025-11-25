package uit.ie303.demo.rooms;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.roomtype.RoomType;
import uit.ie303.demo.roomtype.RoomTypeRepository;

@Service
public class RoomsService {
    private final RoomsRepository repository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public RoomsService(RoomsRepository repository) {
        this.repository = repository;
    }

    public List<Rooms> getAllRooms() {
        return this.repository.findAll();
    }

    public Optional<Rooms> getRoomsByid(Long id) {
        if (null == id)
            return null;
        return this.repository.findById(id);
    }

    public Rooms createRooms(Rooms rooms) {
        if (this.repository.existsByRoomNumber(rooms.getRoom_number())) {
            throw new IllegalArgumentException("Room number already exists");
        }

        // RoomType type = roomTypeRepository.findById(rooms.getTypeId()).orElse(null);
        // rooms.setRoomType(type);

        return this.repository.save(rooms);
    }

    public Rooms updateRooms(Rooms rooms){
        if(null == rooms.getRoom_id()) return null;

        Rooms room = this.repository.findById(rooms.getRoom_id()).orElse(null);

        if(null != room){
            room.setAmenities(rooms.getAmenities());
            room.setRoom_number(rooms.getRoom_number());
            room.setRoom_status(rooms.getRoom_status());
            room.setRoom_view(rooms.getRoom_view());
            room.setType_id(rooms.getType_id());

            // RoomType roomType = roomTypeRepository.findById(rooms.getTypeId()).orElse(null);

            // room.setRoomType(roomType);

            return this.repository.save(room);
        }

        return null;
    }

    public void deleteRoom(Long id) {
        if (null == id)
            return;
        repository.deleteById(id);
    }

}

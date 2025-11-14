package uit.ie303.demo.roomtype;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RoomTypeService {

    private RoomTypeRepository repository;

    public RoomTypeService(RoomTypeRepository repository) {
        this.repository = repository;
    }

    public List<RoomType> getAllRoomType() {
        return this.repository.findAll();
    }

    public Optional<RoomType> getRoomTypeById(Long id) {
        if (null == id)
            return null;

        return this.repository.findById(id);
    }

    public RoomType createRoomType(RoomType type) {
        if (null == type)
            return null;
        return this.repository.save(type);
    }

    public void deleteRoomType(Long id) {
        if (null == id)
            return;
        this.repository.deleteById(id);
    }

    public RoomType updateRoomType(Long id, RoomType roomType) {
        if (null == id)
            return null;

        RoomType rRoomType = this.repository.findById(id).orElse(null);

        if (rRoomType != null) {
            rRoomType.setArea(roomType.getArea());
            rRoomType.setCapacity(roomType.getCapacity());
            rRoomType.setDescription(roomType.getDescription());
            // rRoomType.setId(roomType.getId());
            rRoomType.setImgSrc(roomType.getImgSrc());
            rRoomType.setPrice(roomType.getPrice());
            rRoomType.setTypeName(roomType.getTypeName());

            return this.repository.save(rRoomType);

        }

        return null;
    }
}

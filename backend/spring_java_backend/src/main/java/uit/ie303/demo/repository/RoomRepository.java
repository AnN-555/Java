package uit.ie303.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.ie303.demo.model.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByTypeIdAndRoomViewAndRoomStatus(int typeId, String roomView, String roomStatus);
}

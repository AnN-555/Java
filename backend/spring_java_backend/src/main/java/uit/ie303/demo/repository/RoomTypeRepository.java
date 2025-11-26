package uit.ie303.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    
}

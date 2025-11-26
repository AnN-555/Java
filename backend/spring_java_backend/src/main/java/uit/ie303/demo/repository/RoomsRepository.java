package uit.ie303.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.Rooms;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
    boolean existsByRoomNumber(Integer roomNumber);
    Optional<Rooms> findByRoomNumber(Integer roomNumber);
}
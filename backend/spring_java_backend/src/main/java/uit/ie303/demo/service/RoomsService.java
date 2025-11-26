package uit.ie303.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.RoomType;
import uit.ie303.demo.model.Rooms;
import uit.ie303.demo.repository.RoomTypeRepository;
import uit.ie303.demo.repository.RoomsRepository;

@Service
public class RoomsService {

    @Autowired
    private RoomsRepository repository;

    public List<Rooms> findAll() {
        return repository.findAll();
    }

    public Optional<Rooms> findById(Long id) {
        if (null == id)
            return null;
        return repository.findById(id);
    }

    public Optional<Rooms> findByNumber(Integer number) {
        if (null == number)
            return null;
        return repository.findByRoomNumber(number);
    }

    public Rooms save(Rooms item) {
        if (null == item)
            return null;
        return repository.save(item);
    }

    public boolean delete(Long id) {

        if (null != id && repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}

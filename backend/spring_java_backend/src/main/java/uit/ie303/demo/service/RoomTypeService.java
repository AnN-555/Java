package uit.ie303.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.RoomType;
import uit.ie303.demo.repository.RoomTypeRepository;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository repository;

    public List<RoomType> findAll() {
        return repository.findAll();
    }

    public Optional<RoomType> findById(Long id) {
        if(null == id ) return null;
        return repository.findById(id);
    }

    public RoomType save(RoomType item) {
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

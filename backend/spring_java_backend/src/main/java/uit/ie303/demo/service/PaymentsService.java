package uit.ie303.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.Payments;
import uit.ie303.demo.model.RoomType;
import uit.ie303.demo.repository.PaymentsRepository;
import uit.ie303.demo.repository.RoomTypeRepository;

@Service
public class PaymentsService {

    @Autowired
    private  PaymentsRepository repository;

    public List<Payments> findAll() {
        return repository.findAll();
    }

    public Optional<Payments> findById(Long id) {
        if(null == id ) return null;
        return repository.findById(id);
    }

    public Payments save(Payments item) {
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


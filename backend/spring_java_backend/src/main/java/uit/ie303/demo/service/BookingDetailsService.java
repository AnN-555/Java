package uit.ie303.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.BookingDetails;
import uit.ie303.demo.model.Customer;
import uit.ie303.demo.repository.BookingDetailsRepositoy;
import uit.ie303.demo.repository.CustomerRepository;

@Service
public class BookingDetailsService {

    @Autowired
    private BookingDetailsRepositoy repository;

    public List<BookingDetails> findAll() {
        return repository.findAll();
    }

    public Optional<BookingDetails> findById(Long id) {
        if (null == id)
            return null;
        return repository.findById(id);
    }

    public BookingDetails save(BookingDetails item) {
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

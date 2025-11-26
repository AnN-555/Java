package uit.ie303.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.Booking;
import uit.ie303.demo.model.Customer;
import uit.ie303.demo.repository.BookingRepository;
import uit.ie303.demo.repository.CustomerRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    public List<Booking> findAll() {
        return this.repository.findAll();
    }

    public Optional<Booking> findById(Long id) {
        if (null == id)
            return null;
        return this.repository.findById(id);
    }

    public Booking save(Booking booking){
        if(null == booking) return null;
        return this.repository.save(booking);
    }

    public boolean delete(Long id) {
        if (null == id)
            return false;
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }

}

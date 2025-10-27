package uit.ie303.demo.booking;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:3000") //allow access locally
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingRepository bookingRepo;

    public BookingController(BookingRepository repo){
        this.bookingRepo = repo;

    }
}

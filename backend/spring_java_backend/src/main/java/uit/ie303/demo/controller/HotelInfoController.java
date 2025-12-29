package uit.ie303.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotel")
public class HotelInfoController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getHotelInfo() {
    
        Map<String, Object> hotelInfo = new HashMap<>();
        hotelInfo.put("name", "Diamond Land Hotel");
        hotelInfo.put("address", "Han Thuyen Street, Thu Duc Ward, HCMC");
        

        Map<String, Double> location = new HashMap<>();
        location.put("lat", 10.849);
        location.put("lng", 106.772);
        
        hotelInfo.put("location", location);

 

        return ResponseEntity.ok(hotelInfo);
    }
}

package uit.ie303.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hotel")
public class HotelInfoController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getHotelInfo() {
        // Giả lập dữ liệu lấy từ Database
        Map<String, Object> hotelInfo = new HashMap<>();
        hotelInfo.put("name", "Diamond Land Hotel");
        hotelInfo.put("address", "Han Thuyen Street, Thu Duc Ward, HCMC");
        
        // Đây là phần quan trọng: Tọa độ được gửi từ Server về
        Map<String, Double> location = new HashMap<>();
        location.put("lat", 10.849);
        location.put("lng", 106.772);
        
        hotelInfo.put("location", location);

        // Trả về JSON: 
        // { 
        //   "name": "Diamond Land Hotel", 
        //   "location": { "lat": 10.849, "lng": 106.772 } 
        // }
        return ResponseEntity.ok(hotelInfo);
    }
}

// src/main/java/uit/ie303/demo/controller/FeedbackController.java
package uit.ie303.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.model.FeedbackDTO;
import uit.ie303.demo.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*") // Cho phép tất cả frontend
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // GET: Lấy tất cả feedback cũ
    @GetMapping("/all")
    public ResponseEntity<List<FeedbackDTO>> getAll() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    // POST: Thêm feedback mới
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestBody Map<String, String> body) {
        String name = body.get("customerName");
        String comment = body.get("comment");

        Map<String, Object> resp = new HashMap<>();
        try {
            feedbackService.addFeedback(name, comment);
            resp.put("success", true);
            resp.put("message", "Cảm ơn bạn đã đánh giá!");
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resp);
        }
    }
}
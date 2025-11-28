
package uit.ie303.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import uit.ie303.demo.model.FeedbackDTO;

@Service
public class FeedbackService {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=diamondhotel;encrypt=false;trustServerCertificate=true;";
    private static final String DB_USER = "diamondhotel";
    private static final String DB_PASS = "diamond";

 
    public List<FeedbackDTO> getAllFeedbacks() {
        List<FeedbackDTO> list = new ArrayList<>();
        String sql = "SELECT customer_name, comment, created_at FROM feedback ORDER BY created_at DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FeedbackDTO dto = new FeedbackDTO();
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setComment(rs.getString("comment"));
                dto.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // THÊM FEEDBACK MỚI
    public void addFeedback(String customerName, String comment) throws Exception {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập tên!");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập nội dung đánh giá!");
        }

        String sql = "INSERT INTO feedback (customer_name, comment) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customerName.trim());
            ps.setString(2, comment.trim());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Lưu đánh giá thất bại!");
        }
    }
}
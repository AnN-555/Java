package uit.ie303.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import uit.ie303.demo.model.ReservationDTO;

@Service
public class CustomerService {

    public Integer findExistingCustomer(Connection conn, String email, String phone) throws SQLException {
        String sql = "SELECT customer_id FROM customers WHERE email = ? OR phone_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email != null ? email : "");
            ps.setString(2, phone != null ? phone : "");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    public Integer insertCustomer(Connection conn, ReservationDTO dto) throws SQLException {
        String sql = "INSERT INTO customers (customer_name, phone_number, email, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dto.getCustomerName());
            ps.setString(2, dto.getPhone());
            ps.setString(3, dto.getEmail());
            ps.setString(4, dto.getAddress());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Tạo khách hàng thất bại");
    }
}

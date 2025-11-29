package uit.ie303.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomAvailableService {

    public List<Integer> findAvailableRooms(Connection conn, String roomTypeName, String roomView,
                                             int needed, Timestamp checkIn, Timestamp checkOut) throws SQLException {
        List<Integer> result = new ArrayList<>();
        String sql = """
            SELECT TOP (?) r.room_id
            FROM rooms r
            JOIN roomtype t ON r.type_id = t.type_id
            WHERE t.type_name = ?
              AND r.room_view = ?
              AND NOT EXISTS (
                SELECT 1 FROM booking_details bd
                JOIN booking b ON bd.booking_id = b.booking_id
                WHERE bd.room_id = r.room_id
                  AND NOT (b.checkout_date <= ? OR b.checkin_date >= ?)
              )
            ORDER BY r.room_id
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, needed);
            ps.setString(2, roomTypeName);
            ps.setString(3, roomView);
            ps.setTimestamp(4, checkIn);
            ps.setTimestamp(5, checkOut);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getInt(1));
                }
            }
        }
        return result;
    }
}

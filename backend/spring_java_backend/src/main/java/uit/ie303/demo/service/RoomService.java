package uit.ie303.demo.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uit.ie303.demo.model.RoomDTO;

public class RoomService {

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=diamondhotel;encrypt=false;trustServerCertificate=true;";
    private final String user = "diamondhotel";
    private final String password = "diamond";


    public List<RoomDTO> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<RoomDTO> list = new ArrayList<>();

        String sql = "SELECT DISTINCT rt.type_name, rt.price, rt.image_url ,r.room_view " +
                     "FROM rooms r " +
                     "INNER JOIN roomtype rt ON r.type_id = rt.type_id " +
                     "WHERE r.room_status = 'Available' " +
                     "AND r.room_id NOT IN ( " +
                     "    SELECT bd.room_id " +
                     "    FROM booking_details bd " +
                     "    INNER JOIN booking b ON bd.booking_id = b.booking_id " +
                     "    WHERE b.booking_status IN ('Confirmed','Pending') " +
                     "      AND b.checkin_date < ? " + 
                     "      AND b.checkout_date > ? " + 
                     ")";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(checkOut));
            ps.setDate(2, Date.valueOf(checkIn));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new RoomDTO(
                        rs.getString("type_name"),
                        rs.getInt("price"),
                        rs.getString("room_view"),
                        rs.getString("image_url"),
                        checkIn,
                        checkOut 
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<RoomDTO> getAvailableRooms() {
        List<RoomDTO> list = new ArrayList<>();

        String sql = "SELECT DISTINCT rt.type_name, rt.price,rt.image_url, r.room_view " +
                     "FROM rooms r " +
                     "INNER JOIN roomtype rt ON r.type_id = rt.type_id " +
                     "WHERE r.room_status = 'Available'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new RoomDTO(
                        rs.getString("type_name"),
                        rs.getInt("price"),
                        rs.getString("room_view"),
                        rs.getString("image_url"),
                        null, 
                        null 
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

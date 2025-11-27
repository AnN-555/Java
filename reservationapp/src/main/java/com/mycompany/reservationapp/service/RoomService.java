package com.mycompany.reservationapp.service;

import com.mycompany.reservationapp.dto.RoomDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=diamondhotel;encrypt=false;trustServerCertificate=true;";
    private final String user = "sa";
    private final String password = "123456";


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
                     "      AND b.checkin_date < ? " + // booking bắt đầu trước check-out
                     "      AND b.checkout_date > ? " + // booking kết thúc sau check-in
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
                        checkOut // thêm ngày check-in/check-out cho DTO
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
                        null, // check-in null vì không lọc theo ngày
                        null  // check-out null
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

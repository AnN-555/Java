------------------------------------------------------------
-- 1️⃣  ROOMTYPE (Loại phòng)
------------------------------------------------------------
INSERT INTO roomtype (type_name, price, capacity, type_description, area, image_url)
VALUES
('Standard Room', 700000, 2, 'Phòng tiêu chuẩn, đầy đủ tiện nghi cơ bản.', 25.0, 'https://example.com/images/standard.jpg'),
('Deluxe Room', 1200000, 3, 'Phòng cao cấp, có view đẹp và trang bị hiện đại.', 35.0, 'https://example.com/images/deluxe.jpg'),
('Suite Room', 2200000, 4, 'Phòng suite sang trọng với phòng khách riêng.', 50.0, 'https://example.com/images/suite.jpg'),
('Family Room', 1600000, 5, 'Phòng gia đình rộng rãi, tiện nghi, phù hợp nhóm lớn.', 45.0, 'https://example.com/images/family.jpg'),
('Presidential Suite', 4800000, 6, 'Phòng tổng thống cao cấp nhất khách sạn.', 80.0, 'https://example.com/images/president.jpg');

------------------------------------------------------------
-- 2️⃣  ROOMS (Phòng)
------------------------------------------------------------
INSERT INTO rooms (room_number, amenities, room_view, room_status, type_id)
VALUES
(101, 'Wi-Fi, TV, Máy lạnh', 'Thành phố', 'Available', 1),
(102, 'Wi-Fi, TV, Máy lạnh', 'Sân vườn', 'Available', 1),
(201, 'Wi-Fi, TV, Minibar, Bồn tắm', 'Biển', 'Booked', 2),
(202, 'Wi-Fi, TV, Minibar, Bồn tắm', 'Hồ bơi', 'Available', 2),
(301, 'Wi-Fi, Jacuzzi, Sofa, Tủ lạnh', 'Biển', 'Available', 3),
(302, 'Wi-Fi, Jacuzzi, Sofa, Tủ lạnh', 'Biển', 'Maintenance', 3),
(401, 'Wi-Fi, Sofa, Giường đôi, TV', 'Sân vườn', 'Available', 4),
(402, 'Wi-Fi, Sofa, Giường đôi, TV', 'Thành phố', 'Available', 4),
(501, 'Wi-Fi, Jacuzzi, Minibar, View Biển', 'Biển', 'Available', 5);

------------------------------------------------------------
-- 3️⃣  CUSTOMERS (Khách hàng)
------------------------------------------------------------
INSERT INTO customers (customer_name, phone_number, email, address)
VALUES
(N'Nguyễn Văn A', '0912345678', 'nguyenvana@example.com', N'Hà Nội'),
(N'Trần Thị B', '0987654321', 'tranthib@example.com', N'Hồ Chí Minh'),
(N'Lê Văn C', '0933123456', 'levanc@example.com', N'Đà Nẵng'),
(N'Phạm Thị D', '0909876543', 'phamthid@example.com', N'Cần Thơ'),
(N'Hoàng Minh E', '0977788899', 'hoangminhe@example.com', N'Hải Phòng');



------------------------------------------------------------
-- 4️⃣  BOOKING (Đặt phòng)
------------------------------------------------------------
INSERT INTO booking (customer_id, checkin_date, checkout_date, num_guests, total_price, special_request, booking_status)
VALUES
(1, '2025-11-01 14:00:00', '2025-11-03 12:00:00', 2, 1400000, N'Cần phòng yên tĩnh, tầng cao.', 'Confirmed'),
(2, '2025-11-05 13:00:00', '2025-11-07 11:00:00', 3, 2400000, N'Cần thêm giường phụ.', 'Pending'),
(3, '2025-11-10 14:00:00', '2025-11-15 11:00:00', 4, 11000000, N'Phòng gần hồ bơi.', 'Confirmed'),
(4, '2025-12-01 15:00:00', '2025-12-03 12:00:00', 2, 1400000, NULL, 'Pending'),
(5, '2025-12-20 12:00:00', '2025-12-25 11:00:00', 5, 7500000, N'Phòng view biển.', 'Confirmed');

------------------------------------------------------------
-- 5️⃣  BOOKING DETAILS (Chi tiết đặt phòng)
------------------------------------------------------------
INSERT INTO booking_details (booking_id, room_id)
VALUES
(1, 101),
(2, 201),
(3, 301),
(4, 102),
(5, 501);

------------------------------------------------------------
-- 6️⃣  PAYMENTS (Thanh toán)
------------------------------------------------------------
INSERT INTO payments (booking_id, payment_date, payment_method, amount, payment_status, transaction_code)
VALUES
(1, '2025-10-31 09:30:00', 'Credit Card', 1400000, 'Completed', 'TXN20251031A'),
(2, '2025-11-05 10:00:00', 'Cash', 2400000, 'Pending', NULL),
(3, '2025-11-10 16:00:00', 'Online Banking', 11000000, 'Completed', 'TXN20251110C'),
(4, '2025-12-01 15:30:00', 'Credit Card', 1400000, 'Pending', NULL),
(5, '2025-12-19 18:00:00', 'Online Banking', 7500000, 'Completed', 'TXN20251219E');

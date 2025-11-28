CREATE DATABASE diamondhotel

USE diamondhotel
GO

CREATE TABLE roomtype (
	type_id INT IDENTITY(1,1) PRIMARY KEY,
	type_name VARCHAR(70) NOT NULL,
	price DECIMAL(18,2) NOT NULL,
	capacity INT NOT NULL,
	type_description VARCHAR(100),
	area FLOAT NOT NULL,
	image_url VARCHAR(255) NOT NULL
)

CREATE TABLE rooms (
	room_id INT IDENTITY(1,1) PRIMARY KEY,
	room_number INT NOT NULL UNIQUE,
	type_id INT NOT NULL,
	amenities VARCHAR(200),
	room_view VARCHAR(100),
	room_status VARCHAR(50) NOT NULL,
	FOREIGN KEY (type_id) REFERENCES roomtype(type_id)
)

CREATE TABLE customers (
    customer_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_name NVARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address NVARCHAR(200)
)
GO

CREATE TABLE booking (
    booking_id VARCHAR(50) PRIMARY KEY, 
    customer_id INT NOT NULL,
    checkin_date DATETIME NOT NULL,
    checkout_date DATETIME NOT NULL,
    num_guests INT NOT NULL,
    total_price DECIMAL(18,2),
    special_request NVARCHAR(200),
    booking_status VARCHAR(50) DEFAULT 'Paid', 
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);


CREATE TABLE booking_details (
    booking_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    room_id INT NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
CREATE TABLE payments (
    payment_id INT IDENTITY(1,1) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    payment_date DATETIME DEFAULT GETDATE(),
    payment_method VARCHAR(50),
    amount DECIMAL(18,2) NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'Completed',
    transaction_code VARCHAR(100) NULL,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);


--Đang phân vân
--CREATE TABLE staff (
--    staff_id INT IDENTITY(1,1) PRIMARY KEY,
--    staff_name NVARCHAR(100),
--    position VARCHAR(50),
--    phone_number VARCHAR(15),
--    email VARCHAR(100),
--    password_hash VARCHAR(255)
--)

--CREATE TABLE users (
--    user_id INT IDENTITY(1,1) PRIMARY KEY,
--    username VARCHAR(50) UNIQUE NOT NULL,
--    password_hash VARCHAR(255) NOT NULL,
--    customer_id INT,
--    role VARCHAR(20) DEFAULT 'customer', -- 'customer', 'staff', 'admin'
--    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
--)

--CREATE TABLE reviews (
--    review_id INT IDENTITY(1,1) PRIMARY KEY,
--    customer_id INT NOT NULL,
--    room_id INT NOT NULL,
--    rating INT CHECK (rating BETWEEN 1 AND 5),
--    comment NVARCHAR(300),
--    review_date DATETIME DEFAULT GETDATE(),
--    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
--    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
--)
GO

INSERT INTO roomtype (type_name, price, capacity, type_description, area, image_url)
VALUES 
	('Deluxe Room', 2000, 2, 'Standard room, 1 double bed', 35,'/image/deluxe-room.jpg'),
	('Deluxe Twin Room', 2000, 2, 'Standard room, 2 single beds', 35,'/image/deluxe-twin-room.jpg'),
	('Superior Room', 2500, 2, 'Premium room, 1 double bed', 40,'/image/superior-room.jpg'),
	('Diamond Suite', 3500, 2, 'Luxury suite with separate living room', 50,'/image/diamond-suite.jpg'),
	('President Suite', 6000, 2, 'Luxurious presidential suite with separate living room and meeting room', 139,'/image/president-suite.jpg')
GO

INSERT INTO rooms (room_number, type_id, amenities, room_view, room_status)
VALUES
	--Floor 1
	(101, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(102, 1, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(103, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'City View', 'Available'),
	(104, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(105, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(106, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'City View', 'Available'),
	(107, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(108, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(109, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'City View', 'Available'),
	(110, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(111, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(112, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(113, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(114, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(115, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 2
	(201, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(202, 1, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(203, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'City View', 'Available'),
	(204, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(205, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(206, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'City View', 'Available'),
	(207, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(208, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(209, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'City View', 'Available'),
	(210, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(211, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(212, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(213, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(214, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(215, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 3
	(301, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(302, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'River View', 'Available'),
	(303, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'River View', 'Available'),
	(304, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(305, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(306, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'River View', 'Available'),
	(307, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(308, 2, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'City View', 'Available'),
	(309, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'Garden View', 'Available'),
	(310, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(311, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(312, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(313, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(314, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(315, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 4
	(401, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(402, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'City View', 'Available'),
	(403, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'River View', 'Available'),
	(404, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(405, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(406, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(407, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(408, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(409, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'City View', 'Available'),
	(410, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(411, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(412, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(413, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(414, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(415, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 5
	(501, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(502, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'City View', 'Available'),
	(503, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'City View', 'Available'),
	(504, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(505, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(506, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(507, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(508, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(509, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'Garden View', 'Available'),
	(510, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(511, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(512, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(513, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(514, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(515, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 6
	(601, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(602, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'River View', 'Available'),
	(603, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'River View', 'Available'),
	(604, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(605, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(606, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(607, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(608, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(609, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'Garden View', 'Available'),
	(610, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(611, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(612, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(613, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(614, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(615, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 7
	(701, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(702, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'River View', 'Available'),
	(703, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'River View', 'Available'),
	(704, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(705, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(706, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(707, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(708, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(709, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'Garden View', 'Available'),
	(710, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(711, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(712, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(713, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(714, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(715, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 8
	(801, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(802, 1, 'Free Wi-Fi, Balcony, Air Conditioning, Flat TV', 'River View', 'Available'),
	(803, 1, 'Free Wi-Fi, Bathtub, Air Conditioning, Smart TV', 'River View', 'Available'),
	(804, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(805, 1, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'River View', 'Available'),
	(806, 1, 'Free Wi-Fi, Mini Bar, Coffee Maker, Flat TV', 'City View', 'Available'),
	(807, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'Garden View', 'Available'),
	(808, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'City View', 'Available'),
	(809, 2, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'Garden View', 'Available'),
	(810, 2, 'Free Wi-Fi, Air Conditioning, Flat TV, Mini Bar', 'City View', 'Available'),
	(811, 2, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'Garden View', 'Available'),
	(812, 3, 'Free Wi-Fi, Bathtub, Mini Bar, Coffee Maker', 'River View', 'Available'),
	(813, 3, 'Free Wi-Fi, Air Conditioning, Balcony, River View', 'River View', 'Available'),
	(814, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Smart TV', 'Pool View', 'Available'),
	(815, 4, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker', 'River View', 'Available'),

	--Floor 9 (President Suite)
	(901, 5, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker, Living Room, Smart TV', 'River Front', 'Available'),
	(902, 5, 'Free Wi-Fi, Jacuzzi, Mini Bar, Coffee Maker, Meeting Room, Smart TV', 'River Front', 'Available')
GO

UPDATE [diamondhotel].[dbo].[roomtype]
SET image_url = CASE type_name
    WHEN 'Deluxe Room' THEN '../images/reservation/reservation-page-2/deluxe-room.jpg'
    WHEN 'Deluxe Twin Room' THEN '../images/reservation/reservation-page-2/deluxe-twin-room.png'
    WHEN 'Superior Room' THEN '../images/reservation/reservation-page-2/superior-room.jpg'
    WHEN 'Diamond Suite' THEN '../images/reservation/reservation-page-2/diamond-room.jpg'
    WHEN 'President Suite' THEN '../images/reservation/reservation-page-2/president-room.jpg'
    ELSE image_url 
	END; 

CREATE TABLE feedback (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        customer_name NVARCHAR(255) NOT NULL, 
        comment NVARCHAR(MAX) NOT NULL,       
        created_at DATETIME DEFAULT GETDATE() 
    )

-- Thêm dữ liệu mẫu (Optional)
INSERT INTO feedback (customer_name, comment) VALUES ('Nguyen Van A', 'Khach san rat dep, phuc vu tot!')
INSERT INTO feedback (customer_name, comment) VALUES ('John Doe', 'Amazing view and friendly staff.')

CREATE TRIGGER trg_UpdateRoomStatus_OnBooking
ON booking_details
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE r
    SET r.room_status = 'Booked'
    FROM rooms r
    INNER JOIN inserted i ON r.room_id = i.room_id
    INNER JOIN booking b ON b.booking_id = i.booking_id
    WHERE GETDATE() BETWEEN b.checkin_date AND b.checkout_date;
END;
GO


CREATE TRIGGER trg_UpdateRoomStatus_OnBookingDelete
ON booking_details
AFTER DELETE, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE r
    SET r.room_status = 'Available'
    FROM rooms r
    WHERE r.room_id IN (
        SELECT room_id FROM deleted
    )
    AND NOT EXISTS (
        SELECT 1
        FROM booking_details bd
        INNER JOIN booking b ON bd.booking_id = b.booking_id
        WHERE bd.room_id = r.room_id
        AND GETDATE() BETWEEN b.checkin_date AND b.checkout_date
    );
END;
GO



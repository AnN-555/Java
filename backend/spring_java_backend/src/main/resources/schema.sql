IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'diamondhotel')
CREATE DATABASE diamondhotel;

USE diamondhotel
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'roomtype')
BEGIN
    CREATE TABLE dbo.roomtype (
        type_id INT IDENTITY(1,1) PRIMARY KEY,
        type_name VARCHAR(70) NOT NULL,
        price DECIMAL(18,2) NOT NULL,
        capacity INT NOT NULL,
        type_description VARCHAR(100),
        area FLOAT NOT NULL,
        image_url VARCHAR(255) NOT NULL
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'rooms')
BEGIN
    CREATE TABLE dbo.rooms (
        room_id INT IDENTITY(1,1) PRIMARY KEY,
        room_number INT NOT NULL UNIQUE,
        type_id INT NOT NULL,
        amenities VARCHAR(200),
        room_view VARCHAR(100),
        room_status VARCHAR(50) NOT NULL,
        FOREIGN KEY (type_id) REFERENCES roomtype(type_id)
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'customers')
BEGIN
    CREATE TABLE dbo.customers (
        customer_id INT IDENTITY(1,1) PRIMARY KEY,
        customer_name NVARCHAR(100) NOT NULL,
        phone_number VARCHAR(15) NOT NULL,
        email VARCHAR(100) NOT NULL,
        address NVARCHAR(200)
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'booking')
BEGIN
    CREATE TABLE dbo.booking (
        booking_id VARCHAR(50) PRIMARY KEY, 
        customer_id INT NOT NULL,
        checkin_date DATETIME NOT NULL,
        checkout_date DATETIME NOT NULL,
        num_guests INT NOT NULL,
        total_price DECIMAL(18,2),
        special_request NVARCHAR(200),
        booking_status VARCHAR(50) DEFAULT 'Paid', 
        FOREIGN KEY (customer_id) REFERENCES dbo.customers(customer_id)
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'booking_details')
BEGIN
    CREATE TABLE dbo.booking_details (
        booking_detail_id INT IDENTITY(1,1) PRIMARY KEY,
        booking_id VARCHAR(50) NOT NULL,
        room_id INT NOT NULL,
        FOREIGN KEY (booking_id) REFERENCES dbo.booking(booking_id),
        FOREIGN KEY (room_id) REFERENCES dbo.rooms(room_id)
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'payments')
BEGIN
    CREATE TABLE dbo.payments (
        payment_id INT IDENTITY(1,1) PRIMARY KEY,
        booking_id VARCHAR(50) NOT NULL,
        payment_date DATETIME DEFAULT GETDATE(),
        payment_method VARCHAR(50),
        amount DECIMAL(18,2) NOT NULL,
        payment_status VARCHAR(50) DEFAULT 'Completed',
        transaction_code VARCHAR(100) NULL,
        FOREIGN KEY (booking_id) REFERENCES dbo.booking(booking_id)
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'feedback')
BEGIN
    CREATE TABLE dbo.feedback (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        customer_name NVARCHAR(255) NOT NULL, 
        comment NVARCHAR(MAX) NOT NULL,       
        created_at DATETIME DEFAULT GETDATE()
    );
END
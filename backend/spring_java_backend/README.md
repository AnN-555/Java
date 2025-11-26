# Brief Information
This is Spingboot project for IE303 subject

## Overview
This project will open a local socket with port 9090.
Common URL: http://localhost:9090/api/

Ex: To get data from table booking, URL should be http://localhost:9090/api/booking
For other tables, replace "booking" with table name.

## Entry point
The main function is located at
[directory]\spring_java_backend\src\main\java\uit\ie303\demo\WebBackendDemoApplication.java

## Usage
Open port 1433 on Microsoft SQL Server.
Create all required tables.
Create .env file to place required authentication info.

### env file template
MAIL_USERNAME=
MAIL_PASSWORD=
MAIL_EMAIL=
DB_USER=diamondhotel
DB_PASS=diamond
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=diamondhotel;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8
SQL_SERVER_PORT=9090



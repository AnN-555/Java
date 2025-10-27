# Java
Công nghệ Java

# Hướng dẫn chạy thử:
Tools: Vs Code, Postman (chỉ dùng để test API, có thể xài web test thay thế)

Yêu cầu:
- JDK 17+
- Cài VS code extension: Language Support for Java(TM) by Red Hat (mở file java lên sẽ hiện popup ở góc phải, nhấn đồng ý để cài đặt)
- 

Chạy thử:
- Mở file WebBackendDemoApplication.java trong thư mục src/main/java/uit/ue303/demo
- ở góc bên phải phía trên giao diện của vs code có nút run, chọn Run Java
- Lần đầu cài extension sẽ tốn thời gian để Maven tải các thư viện và build.
- Ánh xạ database vào code sẽ tuân theo mô hình MVC giống như mẫu trong thư mục booking, customers, roomtype.
- Mỗi bảng sẽ cần 3 file, riêng file Repository tạm thời chỉ cần copy interface qua và sửa tên.
- Chạy thử trên chrome: nhập http://localhost:8080/api/booking
- thay thế booking = customers hoặc tên được khai báo ở phía trên class chứa đối tượng từng bảng

# End file
### Happy Coding ###

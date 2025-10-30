package uit.ie303.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class WebBackendDemoApplication {

	public static void main(String[] args) {

		// Tải biến từ file .env vào biến môi trường hệ thống
        try {
            Dotenv dotenv = Dotenv.load();
            dotenv.entries().forEach(entry -> 
                System.setProperty(entry.getKey(), entry.getValue())
            );
        } catch (Exception e) {
            System.out.println("Error get env file: " + e.getMessage());
            // Có thể bỏ qua nếu file .env không bắt buộc (ví dụ: trên Production)
        }


		SpringApplication.run(WebBackendDemoApplication.class, args);
	}

}

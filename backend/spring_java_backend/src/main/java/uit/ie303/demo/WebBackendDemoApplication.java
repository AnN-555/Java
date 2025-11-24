package uit.ie303.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class WebBackendDemoApplication {

	public static void main(String[] args) {

		// load .env file from directory
        try {
            Dotenv dotenv = Dotenv.load();
            dotenv.entries().forEach(entry -> 
                System.setProperty(entry.getKey(), entry.getValue())
            );
        } catch (Exception e) {
            System.out.println("Error get env file: " + e.getMessage());
            // Skip .env if no neccessary (ex: production)
        }


		SpringApplication.run(WebBackendDemoApplication.class, args);
	}

}

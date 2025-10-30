package uit.ie303.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class EnvironmentCheckComponent {
    @Value("${MAIL_USERNAME}")
    private String mailUsername;

    @PostConstruct
    public void checkEnvironmentVariables() {
        System.out.println("Check env");
        System.out.println("MAIL_USERNAME read: " + mailUsername);
    }
}

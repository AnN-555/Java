package uit.ie303.demo.email;

import io.github.cdimascio.dotenv.Dotenv;

public class MailConfig {
    private final Dotenv dotenv = Dotenv.load();

    public String getUserName(){
        return dotenv.get("MAIL_USERNAME");
    }

    public String getPassword(){
        return dotenv.get("MAIL_PASSWORD");
    }
}

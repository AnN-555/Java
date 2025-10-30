package uit.ie303.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

   public void sendMail(String to, String subject, String text){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("23540063@gm.uit.edu.vn");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);

    mailSender.send(message);
    System.out.println("sent mail to...");
   }
}

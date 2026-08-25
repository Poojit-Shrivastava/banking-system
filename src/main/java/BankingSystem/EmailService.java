package BankingSystem;

import org.springframework.stereotype.Component;

@Component
public class EmailService implements NotificationService{
    @Override
    public void sendNotification(String message){
        System.out.println("Email sent:" + message);
    }
}

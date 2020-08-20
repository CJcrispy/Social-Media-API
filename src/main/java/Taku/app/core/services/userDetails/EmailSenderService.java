package Taku.app.core.services.userDetails;

import Taku.app.core.models.email_verification.MailProperties;
import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.users.User;
import Taku.app.core.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("emailSenderService")
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String email_reciever) {

        User user = new User();
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(email_reciever);
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = verificationTokens.get(0);
        }

        System.out.println("verification token: " + verificationToken.getConfirmationToken());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        System.out.println("mail to whom: " + email_reciever);
        mailMessage.setTo(email_reciever);

        mailMessage.setSubject("Complete Registration!");

        mailMessage.setFrom(mailProperties.getUsername());
        System.out.println("mail from whom: " + mailProperties.getUsername());

        mailMessage.setText("To confirm your account, please click here : "
                + mailProperties.getVerificationapi() + verificationToken.getConfirmationToken());


        System.out.println("To confirm your account, please click here : "
                + "http://localhost:4200/verify-email?token="+ verificationToken.getConfirmationToken());

        javaMailSender.send(mailMessage);
    }
}

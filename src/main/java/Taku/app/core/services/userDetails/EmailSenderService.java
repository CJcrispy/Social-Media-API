package Taku.app.core.services.userDetails;

import Taku.app.core.models.email_verification.MailProperties;
import Taku.app.core.models.email_verification.VerificationForm;
import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.users.User;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email, String email_reciever) {

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


        javaMailSender.send(email);
    }
}

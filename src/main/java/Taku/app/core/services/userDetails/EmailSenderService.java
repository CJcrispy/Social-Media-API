package Taku.app.core.services.userDetails;

import Taku.app.core.models.email_verification.MailProperties;
import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.users.User;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("emailSenderService")
public class EmailSenderService {

    @Autowired
    UserRepository userRepository;

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

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email_reciever);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setText("To confirm your account, please click here : "
                + mailProperties.getVerificationapi() + verificationToken.getConfirmationToken());

        javaMailSender.send(mailMessage);
    }

    @Async
    public ResponseEntity<String> retryEmail(String email){

        //Expire old token
        User user = userRepository.findByEmailIgnoreCase(email);
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(email);

        if (verificationTokens.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        VerificationToken verificationTokenz = verificationTokenRepository.findByUserId(user.getId());
        if (verificationTokenz.getExpiredDate().before(new Date(System.currentTimeMillis()))) {
            return ResponseEntity.unprocessableEntity().body("Expired token.");
        }

        if (verificationTokenz.getStatus() == "EXPIRED") {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        if (verificationTokenz.getConfirmedDate() != null) {
            return ResponseEntity.badRequest().body("Token already confirmed.");
        }

        verificationTokenRepository.delete(verificationTokenz);

        //Create new token
        VerificationToken verificationToken;
        verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setText("To confirm your account, please click here : "
                + mailProperties.getVerificationapi() + verificationToken.getConfirmationToken());

        javaMailSender.send(mailMessage);

        return ResponseEntity.ok("Old token expired and new token created.");
    }
}

package Taku.app.core.services.userDetails;

import Taku.app.core.models.users.User;
import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VerificationTokenService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    public VerificationTokenService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, EmailSenderService emailSenderService){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    public ResponseEntity<String> verifyEmail(String token){
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByConfirmationToken(token);
        if (verificationTokens.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        VerificationToken verificationToken = verificationTokens.get(0);
        if (verificationToken.getExpiredDate().before(new Date(System.currentTimeMillis()))) {
            return ResponseEntity.unprocessableEntity().body("Expired token.");
        }

        verificationToken.setConfirmedDate(new Date(System.currentTimeMillis()));
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        Optional<User> person = userRepository.findById(verificationToken.getUser().getId());
        User user = person.get();
        user.setVerified(true);
        userRepository.save(user);
        verificationTokenRepository.save(verificationToken);

        return ResponseEntity.ok("You have successfully verified your email address.");
    }


}

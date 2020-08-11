package Taku.app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import Taku.app.core.models.email_verification.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    List<VerificationToken> findByUserEmail(String email);
    List<VerificationToken> findByToken(String token);
}

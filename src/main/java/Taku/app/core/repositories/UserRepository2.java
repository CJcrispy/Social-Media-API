package Taku.app.core.repositories;

import java.util.List;

import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository2 extends JpaRepository<User, Long> {
//    List<User> findByEmail(String email);

        User findByEmail(String emailId);
}

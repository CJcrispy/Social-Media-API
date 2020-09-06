package Taku.app.core.repositories;

import java.util.List;
import java.util.Optional;

import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String email);

    Boolean exisitsById(Long id);

}
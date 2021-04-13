package Taku.app.core.repositories;

import java.util.List;
import java.util.Optional;

import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String email);

    @Query ("SELECT COUNT(*) FROM User")
    long grabTableRowCount();

    @Query (value = "select * from users u3 left join user_roles ur on u3.id = ur.user_id where ur.role_id = 2 or ur.role_id =1 order by random()",
            nativeQuery = true)
    List<User> grabRandomTableRows();

    @Query(value = "select * from users u3 left join user_roles ur on u3.id = ur.user_id where ur.role_id = 2", nativeQuery = true)
    List<User> grabBusinessUsers();

    @Query(value = "select * from users u2 left join user_roles ur on u2.id = ur.user_id where ur.role_id = 1", nativeQuery = true)
    List<User> grabMemberUsers();
}
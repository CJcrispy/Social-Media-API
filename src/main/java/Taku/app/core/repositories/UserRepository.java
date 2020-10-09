package Taku.app.core.repositories;

import java.util.List;
import java.util.Optional;

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

    @Query (value = "SELECT * FROM users u OFFSET floor(random()*:n) LIMIT 1",
            nativeQuery = true)
    List<User> grabRandomTableRows(@Param("n") long n);

    @Query (value = "select * from users where id = all (select user_id from user_roles where role_id = 2)",
            nativeQuery = true)
    List<User> grabRandomBusinessTableRows(@Param("n") long n);

    @Query (value = "select * from users where id = all (select user_id from user_roles where role_id = 1)",
            nativeQuery = true)
    List<User> grabRandomMemberTableRows(@Param("n") long n);
}
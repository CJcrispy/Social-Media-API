package Taku.app.core.repositories;
import java.util.Optional;

import Taku.app.core.models.ERole;
import Taku.app.core.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(ERole name);
}

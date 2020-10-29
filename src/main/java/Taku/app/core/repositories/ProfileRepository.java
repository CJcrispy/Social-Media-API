package Taku.app.core.repositories;


import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUser(Long id);

}

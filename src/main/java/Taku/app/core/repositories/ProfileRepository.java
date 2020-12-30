package Taku.app.core.repositories;


import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = "select * from profiles p where p.user_id =:user_id",
            nativeQuery = true)
    Profile findByUser(@Param("user_id") Long id);

}

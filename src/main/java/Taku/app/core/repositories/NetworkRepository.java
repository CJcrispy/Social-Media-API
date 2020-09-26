package Taku.app.core.repositories;


import Taku.app.core.models.profile.Network;
import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {

    @Query ("SELECT COUNT(*) FROM Network where user_one =:user AND relationships = 'FOLLOWING'")
    long getFollowers(@Param("user") User user);

    @Query ("SELECT COUNT(*) FROM Network where user_one =:user AND relationships = 'FOLLOWED'")
    long getFollowing(@Param("user") User user);

    @Query ("SELECT COUNT(*) FROM Network where user_one =:user AND relationships = 'FOLLOWING' AND user_two =:user2")
    long isFollowing(@Param("user") User user, @Param("user2") User user2);

    @Transactional
    @Modifying
    @Query("DELETE FROM Network WHERE user_one =:user AND relationships = 'FOLLOWING' and user_two =:user2")
    void removeFollower(@Param("user") User user, @Param("user2") User user2);

    @Transactional
    @Modifying
    @Query("DELETE FROM Network WHERE user_two =:user AND relationships = 'FOLLOWED' AND user_one =:user2")
    void removeFollowing(@Param("user") User user, @Param("user2") User user2);
}

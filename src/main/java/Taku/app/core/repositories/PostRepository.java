package Taku.app.core.repositories;

import Taku.app.core.models.feed.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from posts p where p.username =:username",
            nativeQuery = true)
    List<Post> findAllByUsername(@Param("username")String Username);

}

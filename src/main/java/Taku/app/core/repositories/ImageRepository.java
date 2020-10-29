package Taku.app.core.repositories;

import Taku.app.core.models.profile.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

}

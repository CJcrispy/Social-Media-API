package Taku.app.core.repositories;

import Taku.app.core.models.feed.Total_messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Total_messages, Long> {

}

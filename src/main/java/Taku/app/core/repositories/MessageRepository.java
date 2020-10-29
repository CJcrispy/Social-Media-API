package Taku.app.core.repositories;

import Taku.app.core.models.feed.Messages;
import Taku.app.core.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Long> {

    @Query( value = "SELECT * FROM messages m WHERE m.sender_id =:sender AND m.recipient_id =:recipient " +
            "OR m.sender_id =:recipient AND m.recipient_id =:sender ORDER BY m.posted DESC",
            nativeQuery = true)
    List<Messages> findByRecipientOrSenderOrderByPostedDesc(
            @Param("sender") User sender,
            @Param("recipient") User recipient);

    @Query( value = "SELECT * " +
            "FROM messages m " +
            "WHERE m.message_id IN ( " +
            "SELECT MAX(l.message_id ) " +
            "FROM Messages l " +
            "WHERE l.sender_id = :person OR l.recipient_id = :person " +
            "GROUP BY " +
            "CASE " +
                    "WHEN l.recipient_id = :person THEN l.sender_id " +
            "WHEN l.sender_id = :person THEN l.recipient_id " +
            "ELSE :person " +
            "END) ORDER BY m.posted desc",
            nativeQuery = true)
    List<Messages> findLastMessagesByPerson(@Param("person") User person);



}

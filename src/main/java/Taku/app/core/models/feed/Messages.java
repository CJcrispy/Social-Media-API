package Taku.app.core.models.feed;

import Taku.app.core.models.users.User;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="message_id")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(name = "sender_id")
    private Long sender;

    @Column(name = "recipient_id")
    private Long recipient;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date posted;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "conversation_id")
    private Total_messages conversationID;

    //message creation
    public Messages(String message, User sender, User recipient, Total_messages messages) {
        this.message = message;
        this.sender = sender.getId();
        this.recipient = recipient.getId();
        this.conversationID = messages;
        this.posted = new Date();;
    }

    public Messages(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public Total_messages getConversationID() {
        return conversationID;
    }

    public void setConversationID(Total_messages conversationID) {
        this.conversationID = conversationID;
    }
}

package Taku.app.core.models.feed;

import Taku.app.core.models.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "total_messages")
public class Total_messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="conversation_id")
    private Long id;

    private int total_messages;

    @ManyToOne(targetEntity = Messages.class, fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinColumn(name = "messages")
    private Messages messages;


    private Long[] conversation_members;
    //
    public Total_messages(int total_messages, Long[] members) {

        this.total_messages = total_messages;
        this.conversation_members = members;
    }

    public Total_messages(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotal_messages() {
        return total_messages;
    }

    public void setTotal_messages(int total_messages) {
        this.total_messages = total_messages;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public Long[] getConversation_members() {
        return conversation_members;
    }

    public void setConversation_members(Long[] conversation_members) {
        this.conversation_members = conversation_members;
    }
}

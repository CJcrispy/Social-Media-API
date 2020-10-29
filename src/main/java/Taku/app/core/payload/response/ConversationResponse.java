package Taku.app.core.payload.response;

import Taku.app.core.models.users.User;

import java.util.Date;

public class ConversationResponse {

    private String Message;
    private Long sender;
    private Long recipient;
    private Date posted;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
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
}


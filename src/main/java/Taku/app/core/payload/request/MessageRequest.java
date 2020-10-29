package Taku.app.core.payload.request;

import javax.validation.constraints.NotBlank;

public class MessageRequest {

    @NotBlank
    private String sender;

    @NotBlank
    private String recipient;

    @NotBlank
    private String message;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

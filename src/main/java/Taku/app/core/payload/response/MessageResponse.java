package Taku.app.core.payload.response;

import Taku.app.core.models.users.User;

import java.util.Date;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

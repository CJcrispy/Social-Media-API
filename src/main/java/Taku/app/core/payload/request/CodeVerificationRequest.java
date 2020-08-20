package Taku.app.core.payload.request;

import javax.validation.constraints.NotBlank;

public class CodeVerificationRequest {

    @NotBlank
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

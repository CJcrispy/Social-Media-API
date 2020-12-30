package Taku.app.core.payload.response;

import Taku.app.core.models.profile.Profile;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long user_id;

    public JwtResponse(String accessToken, Long id) {
        this.token = accessToken;
        this.user_id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return user_id;
    }

    public void setId(Long id) {
        this.user_id = id;
    }
}

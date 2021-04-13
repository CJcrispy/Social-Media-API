package Taku.app.core.payload.request;

import javax.validation.constraints.NotBlank;

public class FollowerRequest {

    @NotBlank
    private String userEmail;

    @NotBlank
    private String followerEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFollowerEmail() {
        return followerEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        this.followerEmail = followerEmail;
    }
}

package Taku.app.core.payload.request;

import javax.validation.constraints.NotBlank;

public class FollowerRequest {

    @NotBlank
    private String UserEmail;

    @NotBlank
    private String FollowerEmail;

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getFollowerEmail() {
        return FollowerEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        FollowerEmail = followerEmail;
    }
}

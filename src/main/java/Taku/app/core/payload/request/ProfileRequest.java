package Taku.app.core.payload.request;

import javax.validation.constraints.NotBlank;

public class ProfileRequest {

    @NotBlank
    private Long UserId;

    @NotBlank
    private String profileLink;

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }
}

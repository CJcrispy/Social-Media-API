package Taku.app.core.payload.request;

import Taku.app.core.models.users.User;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;

public class ProfileRequest {

    @NotBlank
    private Long UserId;

    @NotBlank
    private String bio;

    @NotBlank
    private String occupation;

    @NotBlank
    private String profileLink;

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }
}

package Taku.app.core.payload.response;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.Roles;
import Taku.app.core.models.users.User;

import java.util.HashMap;
import java.util.Set;

public class ProfileResponse {

    private Long id;
    private String first_name;
    private String last_name;
    private String business_name;
    private String email;
    private Set<Roles> roles;
    private boolean isVerified;
    private Profile profile;
    private String bio;
    private String occupation;
    private int followers;
    private int following;
    private String link;

    public ProfileResponse(Long id, String first_name,
                           String last_name, String business_name, String email, boolean verified,
                           String bio, String occupation,
                           HashMap<Integer, User> followers, HashMap<Integer, User> following,
                           String link) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.business_name = business_name;
        this.email = email;
//        this.roles = roles;
        this.isVerified = verified;
        this.bio = bio;
        this.occupation = occupation;
        this.followers = followers.size();
        this.following = following.size();
        this.link = link;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Roles> getRoles() {
        return roles;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Profile getProfile(){
        return profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
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

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

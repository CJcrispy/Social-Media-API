package Taku.app.core.models.profile;

import Taku.app.core.models.users.User;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(	name = "profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "link")
        })
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name="profile_id")
    private Long profileId;

    private String bio;

    private String occupation;

    @Column(nullable = false)
    private HashMap<Integer, User> followers;

    @Column(nullable = false)
    private HashMap<Integer, User> following;

    @Column(nullable = false)
    private String link;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //for profile creation
    public Profile(User user){
        this.user = user;
        this.bio = null;
        this.occupation = null;
        this.followers = new HashMap<>();
        this.following = new HashMap<>();
        this.link = UUID.randomUUID().toString();

    }

    //for retrieving profile data
    public Profile(){
        super();
    }

    public Long getProfile_id() {
        return profileId;
    }

    public void setProfile_id(Long profile_id) {
        this.profileId = profile_id;
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

    public HashMap<Integer, User> getFollowers() {
        return followers;
    }

    public void setFollowers(HashMap<Integer, User> followers) {
        this.followers = followers;
    }

    public HashMap<Integer, User> getFollowing() {
        return following;
    }

    public void setFollowing(HashMap<Integer, User> following) {
        this.following = following;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

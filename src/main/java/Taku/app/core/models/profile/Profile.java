package Taku.app.core.models.profile;

import Taku.app.core.models.users.Roles;
import Taku.app.core.models.users.User;
import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="profile_id")
    private Long profileId;

    private String bio;

    private String occupation;

    @Column(nullable = false)
    private long followers;

    @Column(nullable = false)
    private long following;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String avatar_reference;

    @Column(nullable = false)
    private String cover_image_reference;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //for profile creation
    public Profile(User user) {
        this.user = user;
        this.bio = null;
        this.occupation = null;
        this.link = UUID.randomUUID().toString();
        this.avatar_reference = "https://taku-images.s3.amazonaws.com/default_image.png";
        this.cover_image_reference = "https://taku-images.s3.amazonaws.com/Solid_grey.svg.png";
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

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAvatar_reference() {
        return avatar_reference;
    }

    public void setAvatar_reference(String avatar_reference) {
        this.avatar_reference = avatar_reference;
    }

    public String getCover_image_reference() {
        return cover_image_reference;
    }

    public void setCover_image_reference(String cover_image_reference) {
        this.cover_image_reference = cover_image_reference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

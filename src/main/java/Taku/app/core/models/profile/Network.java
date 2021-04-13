package Taku.app.core.models.profile;

import Taku.app.core.models.users.User;

import javax.persistence.*;

@Entity
@Table(name = "network")
public class Network {

    public static final String STATUS_NONE = "NONE";
    public static final String STATUS_FOLLOWING = "FOLLOWING";
    public static final String STATUS_FOLLOWED = "FOLLOWED";
    public static final String STATUS_BLOCKED = "BLOCKED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name="network_id")
    private Long networkId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_one")
    private User user_one;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_two")
    private User user_two;

    @Column(nullable = false)
    private String relationships;

    //when hitting the follow button
    public Network(User user_one, User user_two){
        this.user_one = user_one;
        this.user_two = user_two;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public User getUser_one() {
        return user_one;
    }

    public void setUser_one(User user_one) {
        this.user_one = user_one;
    }

    public User getUser_two() {
        return user_two;
    }

    public void setUser_two(User user_two) {
        this.user_two = user_two;
    }

    public String getRelationships() {
        return relationships;
    }

    public void setRelationships(String relationships) {
        this.relationships = relationships;
    }
}

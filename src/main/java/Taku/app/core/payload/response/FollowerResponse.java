package Taku.app.core.payload.response;

import Taku.app.core.models.users.User;

import javax.persistence.Column;
import java.util.HashMap;

public class FollowerResponse {

    private int followers;


    private int following;

    public FollowerResponse(HashMap followers, HashMap following){
        this.followers = followers.size();
        this.following = following.size();
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
}

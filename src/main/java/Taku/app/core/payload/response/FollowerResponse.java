package Taku.app.core.payload.response;

import Taku.app.core.models.users.User;

import javax.persistence.Column;
import java.util.HashMap;

public class FollowerResponse {

    private long followers;


    private long following;

    public FollowerResponse(long followers, long following){
        this.followers = followers;
        this.following = following;
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
}

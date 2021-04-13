package Taku.app.core.payload.response;

public class DiscoveryPageResponse {

    private String username;
    private String image_reference;
    private Long followers;
    private Long following;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_reference() {
        return image_reference;
    }

    public void setImage_reference(String image_reference) {
        this.image_reference = image_reference;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    public Long getFollowing() {
        return following;
    }

    public void setFollowing(Long following) {
        this.following = following;
    }
}

package Taku.app.core.payload.response;

import Taku.app.core.models.profile.Profile;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String first_name;
    private String last_name;
    private String business_name;
    private String email;
    private List<String> roles;
    private boolean isVerified;
    private Profile profile;

    public JwtResponse(String accessToken, Long id, String first_name,
                       String last_name, String business_name, String email, boolean verified,
                       Profile profile,
                       List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.business_name = business_name;
        this.email = email;
        this.roles = roles;
        this.isVerified = verified;
        this.profile = profile;
    }


    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
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


    public List<String> getRoles() {
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


}

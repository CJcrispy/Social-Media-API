package Taku.app.core.services.userDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Taku.app.core.models.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String first_name;

    private String last_name;

    private String business_name;

    private String email;

    private boolean isVerified;

    private String bio;

    private String occupation;

    private String link;

    private long followers;

    private long following;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String first_name,
                           String last_name, String business_name, String email, String password,
                           boolean isVerified, String bio, String occupation, String link,
                           long followers, long following,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.business_name = business_name;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.bio = bio;
        this.occupation = occupation;
        this.link = link;
        this.followers = followers;
        this.following = following;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getBusiness_name(),
                user.getEmail(),
                user.getPassword(),
                user.isVerified(),
                user.getProfile().getBio(),
                user.getProfile().getOccupation(),
                user.getProfile().getLink(),
                user.getProfile().getFollowers(),
                user.getProfile().getFollowing(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getBio() {
        return bio;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getLink() {
        return link;
    }

    public long getFollowers() {
        return followers;
    }

    public long getFollowing() {
        return following;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}

package Taku.app.core.models.profile;
import Taku.app.core.models.users.User;

import javax.persistence.*;

@Entity
@Table( name = "images"
        )
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="profile_id")
    private Long id;

    @Column(nullable = false)
    private String photo_reference;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Images(String photo_reference, User user){
        this.photo_reference = photo_reference;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

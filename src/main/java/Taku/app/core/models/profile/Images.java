package Taku.app.core.models.profile;
import Taku.app.core.models.users.User;

import javax.persistence.*;

@Entity
@Table( name = "images"
        )
public class Images {

    public static final String TYPE_POST = "POST";
    public static final String TYPE_AVATAR = "AVATAR";
    public static final String TYPE_COVER = "COVER_IMAGE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="profile_id")
    private Long id;

    @Column(nullable = false)
    private String photo_reference_id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private String photo_type;

    public Images(String photo_reference, User user){
        this.photo_reference_id = photo_reference;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto_reference() {
        return photo_reference_id;
    }

    public void setPhoto_reference(String photo_reference_id) {
        this.photo_reference_id = photo_reference_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoto_type() {
        return photo_type;
    }

    public void setPhoto_type(String photo_type) {
        this.photo_type = photo_type;
    }
}

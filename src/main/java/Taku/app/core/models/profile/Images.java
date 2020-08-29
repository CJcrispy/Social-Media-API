package Taku.app.core.models.profile;
import javax.persistence.*;

@Entity
@Table( name = "images"
        )
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name="profile_id")
    private Long id;

    private String photo_reference;
}

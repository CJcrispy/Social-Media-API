package Taku.app.core.models.profile;

import javax.persistence.*;

@Entity
@Table(	name = "network")
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name="network_id")
    private Long networkId;
}

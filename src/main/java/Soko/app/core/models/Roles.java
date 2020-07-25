package Soko.app.core.models;

import Soko.app.core.models.ERole;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ROLES")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int role_id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;


    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}

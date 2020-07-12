package core.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "BUSINESS")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private int business_id;

    @Column(nullable = false)
    private String business_name;

    @Column(nullable = false)
    private String business_rep_first_name;

    @Column(nullable = false)
    private String business_rep_last_name;

    @Column(nullable = false, unique = true)
    private String business_email;

    @Column(nullable = false, length = 64)
    private String hashedPass;

    @Column(nullable = false, length = 18)
    private String salt;

    @OneToMany(
            mappedBy = "business",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MemberToken> tokens;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "business_roles",
            joinColumns = @JoinColumn(name = "business_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_rep_first_name() {
        return business_rep_first_name;
    }

    public void setBusiness_rep_first_name(String business_rep_first_name) {
        this.business_rep_first_name = business_rep_first_name;
    }

    public String getBusiness_rep_last_name() {
        return business_rep_last_name;
    }

    public void setBusiness_rep_last_name(String business_rep_last_name) {
        this.business_rep_last_name = business_rep_last_name;
    }

    public String getBusiness_email() {
        return business_email;
    }

    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

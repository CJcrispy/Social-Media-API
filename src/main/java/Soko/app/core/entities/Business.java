package Soko.app.core.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BUSINESS")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private int business_id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String business_name;

    @Column(nullable = false, unique = true)
    private String business_email;

    @Column(nullable = false, length = 64)
    private String hashedPass;

    @Column(nullable = false, length = 64)
    private String salt;

    @OneToMany(
            mappedBy = "business",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BusinessToken> tokens;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="business_role",
            joinColumns={@JoinColumn(name="business_id", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="ID")})
    private List<Roles> roles;

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

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = last_name;
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((business_email== null) ? 0 : business_email.hashCode());
        result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
        result = prime * result + ((hashedPass == null) ? 0 : hashedPass.hashCode());
        result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
        result = prime * result + ((salt == null) ? 0 : salt.hashCode());
        result = prime * result + business_id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Business other = (Business) obj;
        if (business_email == null) {
            if (other.business_email != null)
                return false;
        } else if (!business_email.equals(other.business_email))
            return false;
        if (first_name == null) {
            if (other.first_name != null)
                return false;
        } else if (!first_name.equals(other.first_name))
            return false;
        if (hashedPass == null) {
            if (other.hashedPass != null)
                return false;
        } else if (!hashedPass.equals(other.hashedPass))
            return false;
        if (last_name == null) {
            if (other.last_name != null)
                return false;
        } else if (!last_name.equals(other.last_name))
            return false;
        if (salt == null) {
            if (other.salt != null)
                return false;
        } else if (!salt.equals(other.salt))
            return false;
        if (business_id != other.business_id)
            return false;
        return true;
    }




    public Business(String firstName, String lastName, String hashedpass, String salt,
                  String business_email, String business_name) {
        super();
        this.first_name = firstName;
        this.last_name = lastName;
        this.hashedPass = hashedpass;
        this.salt = salt;
        this.business_email = business_email;
        this.business_name = business_name;
    }

    public Business() {
        super();
    }
}



package Soko.app.core.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MEMBERS")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int user_id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 64)
    private String hashedPass;

    @Column(nullable = false, length = 64)
    private String salt;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MemberToken> tokens;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="member_role",
            joinColumns={@JoinColumn(name="member_id", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="ID")})
    private List<Roles> roles;

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String user_email) {
        this.email = user_email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email== null) ? 0 : email.hashCode());
        result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
        result = prime * result + ((hashedPass == null) ? 0 : hashedPass.hashCode());
        result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
        result = prime * result + ((salt == null) ? 0 : salt.hashCode());
        result = prime * result + user_id;
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
        Member other = (Member) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
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
        if (user_id != other.user_id)
            return false;
        return true;
    }

    public Member(String firstName, String lastName, String hashedpass, String salt,
                  String email) {
        super();
        this.first_name = firstName;
        this.last_name = lastName;
        this.hashedPass = hashedpass;
        this.salt = salt;
        this.email = email;
    }

    public Member() {
        super();
        // TODO Auto-generated constructor stub
    }


}

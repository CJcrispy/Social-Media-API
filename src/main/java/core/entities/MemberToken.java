package core.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "membertokens")
public class MemberToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;

    @Column(nullable = false)
    private Timestamp expiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email", nullable = false)
    private Member email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getEmail() {
        return email;
    }

    public void setEmail(Member email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberToken token = (MemberToken) o;
        if (expiration == null) {
            if (token.expiration != null)
                return false;
        } else if (!expiration.equals(token.expiration))
            return false;
        if (id != token.id)
            return false;
        if (member == null) {
            if (token.member != null)
                return false;
        } else if (!member.equals(token.member))
            return false;
        if (email == null) {
            if (token.email != null)
                return false;
        } else if (!email.equals(token.email))
            return false;
        return id == token.id &&
                Objects.equals(expiration, token.expiration) &&
                Objects.equals(member, token.member) &&
                Objects.equals(email, token.email);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiration == null) ? 0 : expiration.hashCode());
        result = prime * result + id;
        result = prime * result + ((member == null) ? 0 : member.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return "Token{" +
                "id: " + id +
                ", expiration: " + expiration +
                ", user: " + member +
                ", email: " + email +
                '}';
    }

    public MemberToken(Member u, Member email) {
        super();
        this.member = u;
        this.email = email;
        this.expiration = new Timestamp(System.currentTimeMillis() + 10800000);
    }

    public MemberToken() {
        super();
    }
}

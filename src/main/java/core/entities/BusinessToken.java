package core.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "buinessTokens")
public class BusinessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;

    @Column(nullable = false)
    private Timestamp expiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="business_email", nullable = false)
    private Business email;

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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {this.business = business;}

    public Business getEmail() {
        return email;
    }

    public void setEmail(Business email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessToken token = (BusinessToken) o;
        if (expiration == null) {
            if (token.expiration != null)
                return false;
        } else if (!expiration.equals(token.expiration))
            return false;
        if (id != token.id)
            return false;
        if (business == null) {
            if (token.business != null)
                return false;
        } else if (!business.equals(token.business))
            return false;
        if (email == null) {
            if (token.email != null)
                return false;
        } else if (!email.equals(token.email))
            return false;
        return id == token.id &&
                Objects.equals(expiration, token.expiration) &&
                Objects.equals(business, token.business) &&
                Objects.equals(email, token.email);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiration == null) ? 0 : expiration.hashCode());
        result = prime * result + id;
        result = prime * result + ((business == null) ? 0 : business.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return "Token{" +
                "id: " + id +
                ", expiration: " + expiration +
                ", user: " + business +
                ", email: " + email +
                '}';
    }

    public BusinessToken(Business u, Business email) {
        super();
        this.business = u;
        this.email = email;
        this.expiration = new Timestamp(System.currentTimeMillis() + 10800000);
    }

    public BusinessToken() {
        super();
    }
}

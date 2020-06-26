package core.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;

    @Column(nullable = false)
    private Timestamp expiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username", nullable = false)
    private User username;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        if (expiration == null) {
            if (token.expiration != null)
                return false;
        } else if (!expiration.equals(token.expiration))
            return false;
        if (id != token.id)
            return false;
        if (user == null) {
            if (token.user != null)
                return false;
        } else if (!user.equals(token.user))
            return false;
        if (username == null) {
            if (token.username != null)
                return false;
        } else if (!username.equals(token.username))
            return false;
        return id == token.id &&
                Objects.equals(expiration, token.expiration) &&
                Objects.equals(user, token.user) &&
                Objects.equals(username, token.username);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiration == null) ? 0 : expiration.hashCode());
        result = prime * result + id;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", expiration=" + expiration +
                ", user=" + user +
                ", username=" + username +
                '}';
    }

    public Token(User u, User username) {
        super();
        this.user = u;
        this.username = username;
        this.expiration = new Timestamp(System.currentTimeMillis() + 10800000);
    }

    public Token() {
        super();
    }
}

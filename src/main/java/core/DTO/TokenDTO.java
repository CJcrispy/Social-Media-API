package core.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class TokenDTO {

    private int tokenID;
    private int userID;
    private String email;
    private Timestamp expiry;

    public int getTokenID() {
        return tokenID;
    }

    public void setTokenID(int tokenID) {
        this.tokenID = tokenID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = email;
    }

    public Timestamp getExpiry() {
        return expiry;
    }

    public void setExpiry(Timestamp expiry) {
        this.expiry = expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenDTO tokenDTO = (TokenDTO) o;
        return tokenID == tokenDTO.tokenID &&
                userID == tokenDTO.userID &&
                Objects.equals(email, tokenDTO.email) &&
                Objects.equals(expiry, tokenDTO.expiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenID, userID, email, expiry);
    }

    @Override
    public String toString() {
        return "TokenDTO{" +
                "tokenID: " + tokenID +
                ", userID: " + userID +
                ", username: '" + email + '\'' +
                ", expiry: " + expiry +
                '}';
    }

    public TokenDTO(int tokenID, int userID, String email, Timestamp expiry) {
        super();
        this.tokenID = tokenID;
        this.userID = userID;
        this.email = email;
        this.expiry = expiry;
    }
    public TokenDTO() {
        super();
    }
}

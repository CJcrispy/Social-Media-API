package core.DTO;

import java.util.Objects;

public class LoginDTO {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDTO loginDTO = (LoginDTO) o;
        if (password == null) {
            if (loginDTO.password != null)
                return false;
        } else if (!password.equals(loginDTO.password))
            return false;
        if (email == null) {
            if (loginDTO.email != null)
                return false;
        } else if (!email.equals(loginDTO.email))
            return false;
        return Objects.equals(email, loginDTO.email) &&
                Objects.equals(password, loginDTO.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    public LoginDTO(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public LoginDTO(){
        super();
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



}

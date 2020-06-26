package core.DTO;

import java.util.Objects;

public class LoginDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (username == null) {
            if (loginDTO.username != null)
                return false;
        } else if (!username.equals(loginDTO.username))
            return false;
        return Objects.equals(username, loginDTO.username) &&
                Objects.equals(password, loginDTO.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    public LoginDTO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public LoginDTO(){
        super();
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



}

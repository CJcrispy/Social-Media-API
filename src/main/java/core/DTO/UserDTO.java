package core.DTO;

import java.util.Objects;

public class UserDTO {

    private String fname;
    private String lname;
    private String email;
    private String username;
    private String password;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(fname, userDTO.fname) &&
                Objects.equals(lname, userDTO.lname) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname, lname, email, username, password);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserDTO(String fname, String lname, String email, String username, String password) {
        super();
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public UserDTO() {
        super();
    }
}

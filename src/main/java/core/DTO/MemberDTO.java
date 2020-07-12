package core.DTO;

import java.util.Objects;

public class MemberDTO {

    private String fname;
    private String lname;
    private String email;
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
        MemberDTO memberDTO = (MemberDTO) o;
        return Objects.equals(fname, memberDTO.fname) &&
                Objects.equals(lname, memberDTO.lname) &&
                Objects.equals(email, memberDTO.email) &&
                Objects.equals(password, memberDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname, lname, email, password);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public MemberDTO(String fname, String lname, String email, String password) {
        super();
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public MemberDTO() {
        super();
    }
}

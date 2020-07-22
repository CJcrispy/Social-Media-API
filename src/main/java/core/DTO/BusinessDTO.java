package core.DTO;

import core.entities.Business;

import java.util.Objects;

public class BusinessDTO {

    private String fname;
    private String lname;
    private String business_name;
    private String business_email;
    private String password;

    public String getFirstName() {
        return fname;
    }

    public void setFirstName(String FirstName) {
        this.fname = fname;
    }

    public String getLastName() {
        return lname;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public void setLastName(String lname) {
        this.lname = lname;
    }

    public String getBusiness_email() {
        return business_email;
    }

    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
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
        BusinessDTO that = (BusinessDTO) o;
        return fname.equals(that.fname) &&
                lname.equals(that.lname) &&
                business_name.equals(that.business_name) &&
                business_email.equals(that.business_email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname, lname, business_name, business_email, password);
    }

    @Override
    public String toString() {
        return "BusinessDTO{" +
                "rep_fname='" + fname + '\'' +
                ", rep_lname='" + lname + '\'' +
                ", business_name='" + business_name + '\'' +
                ", business_email='" + business_email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public BusinessDTO(String rep_first_name, String rep_last_name, String business_name,
                       String business_email, String password) {
        this.fname = rep_first_name;
        this.lname = rep_last_name;
        this.business_name = business_name;
        this.business_email = business_email;
        this.password = password;
    }

    public BusinessDTO() {
        super();
    }
}

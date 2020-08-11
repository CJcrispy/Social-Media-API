package Taku.app.core.models.users;

import Taku.app.core.models.email_verification.VerificationToken;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(max = 20)
        @Column(nullable = false)
        private String first_name;

        @NotBlank
        @Size(max = 30)
        @Column(nullable = false)
        private String last_name;

        @Size(max = 50)
        private String business_name;

        @NotBlank
        @Size(max = 50)
        @Email
        @Column(nullable = false)
        private String email;

        @NotBlank
        @Size(max = 120)
        @Column(nullable = false)
        private String password;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Roles> roles = new HashSet<>();

        @NotBlank
        @Size(max = 10)
        @Column(nullable = false)
        private boolean isVerified;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        private VerificationToken verificationToken;

        public User() {
        }

        public User(String first_name, String last_name, String business_name,
                    String email, String password) {
                this.first_name = first_name;
                this.last_name = last_name;
                this.business_name = business_name;
                this.email = email;
                this.password = password;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFirst_name() {
                return first_name;
        }

        public void setFirst_name(String first_name) {
                this.first_name = first_name;
        }

        public String getLast_name() {
                return last_name;
        }

        public void setLast_name(String last_name) {
                this.last_name = last_name;
        }

        public String getBusiness_name() {
                return business_name;
        }

        public void setBusiness_name(String business_name) {
                this.business_name = business_name;
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

        public Set<Roles> getRoles() {
                return roles;
        }

        public void setRoles(Set<Roles> roles) {
                this.roles = roles;
        }

        public boolean isVerified() {
                return isVerified;
        }

        public void setVerified(boolean isVerified) {
                this.isVerified = isVerified;
        }

        public VerificationToken getVerificationToken() {
                return verificationToken;
        }

        public void setVerificationToken(VerificationToken verificationToken) {
                this.verificationToken = verificationToken;
        }

}

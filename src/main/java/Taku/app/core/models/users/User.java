package Taku.app.core.models.users;

import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.profile.Network;
import Taku.app.core.models.profile.Profile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users", schema = "public",
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
        private String username;

        @NotBlank
        @Size(max = 30)
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

        private boolean isVerified;

        @OneToOne(mappedBy = "user", orphanRemoval=true, cascade = CascadeType.DETACH)
        private VerificationToken verificationToken;

        @OneToOne(mappedBy = "user", orphanRemoval=true, cascade = CascadeType.DETACH)
        private Profile profile;

        public User() {
        }

        public User(String username, String first_name, String last_name, String business_name,
                    String email, String password) {
                this.username = username;
                this.first_name = first_name;
                this.last_name = last_name;
                this.business_name = business_name;
                this.email = email;
                this.password = password;
                this.isVerified = false;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
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

        public Profile getProfile(){
                return profile;
        }

        public void setProfile(Profile profile){
                this.profile = profile;
        }


}

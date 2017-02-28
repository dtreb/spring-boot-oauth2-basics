package com.dtreb.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

/**
 * User entity.
 *
 * @author dtreb
 */
@Entity
public class User {

    @Id
    @Column(updatable = false, nullable = false)
    @Size(max = 50)
    private String username;

    @Size(max = 80)
    private String password;

    @Email
    @Size(max = 50)
    private String email;

    private boolean activated;

    @Size(max = 100)
    @Column(name = "activationkey")
    private String activationKey;

    @Size(max = 100)
    @Column(name = "resetpasswordkey")
    private String resetPasswordKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority")
    )

    private Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public User setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public User setActivationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public String getResetPasswordKey() {
        return resetPasswordKey;
    }

    public User setResetPasswordKey(String resetPasswordKey) {
        this.resetPasswordKey = resetPasswordKey;
        return this;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public User setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return activated == user.activated &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(activationKey, user.activationKey) &&
                Objects.equals(resetPasswordKey, user.resetPasswordKey) &&
                Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, activated, activationKey, resetPasswordKey, authorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", activated=" + activated +
                ", activationKey='" + activationKey + '\'' +
                ", resetPasswordKey='" + resetPasswordKey + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}

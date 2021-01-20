package com.example.LoginService.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserRolePK implements Serializable {

    @ManyToOne
    @JoinColumn(name="email")
    private User user;

    public UserRolePK() {
    }

    public UserRolePK(User user, Integer role) {
        this.user = user;
        this.role = role;
    }

    public int getRole() {

        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public UserRolePK(User user, int role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @JoinColumn(name="roleId")
    private Integer role;

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserRolePK)) {
            return false;
        }
        UserRolePK castOther = (UserRolePK)other;
        return
                this.user.equals(castOther.user)
                        && this.role.equals(castOther.role);
    }
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.user.hashCode();
        hash = hash * prime + this.role.hashCode();

        return hash;
    }
}

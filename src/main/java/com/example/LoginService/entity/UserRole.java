package com.example.LoginService.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="userrole")
public class UserRole implements Serializable {

    @EmbeddedId
    private  UserRolePK userRolePK;

    public UserRole() {
    }

    public UserRole(UserRolePK userRolePK) {
        this.userRolePK = userRolePK;
    }

    public UserRolePK getUserRolePK() {
        return userRolePK;
    }

    public void setUserRolePK(UserRolePK userRolePK) {
        this.userRolePK = userRolePK;
    }
}

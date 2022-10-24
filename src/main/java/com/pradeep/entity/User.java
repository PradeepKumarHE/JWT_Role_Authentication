package com.pradeep.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "UserRole",joinColumns = { @JoinColumn(name = "UserId") },
           inverseJoinColumns = { @JoinColumn(name = "RoleId") })
    private Set<Role> roles;
}

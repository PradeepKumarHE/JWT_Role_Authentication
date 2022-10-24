package com.pradeep.controller;

import com.pradeep.entity.Role;
import com.pradeep.entity.User;
import com.pradeep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers(){
        userService.initRolesAndUser();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "user";
    }
}

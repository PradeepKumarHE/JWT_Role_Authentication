package com.pradeep.service;

import com.pradeep.entity.Role;
import com.pradeep.entity.User;
import com.pradeep.repository.RolesRepository;
import com.pradeep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void initRolesAndUser(){
        Role adminRole=new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        rolesRepository.save(adminRole);

        Role userRole=new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role");
        rolesRepository.save(userRole);

        User adminUser=new User();
        adminUser.setFirstname("Nandini");
        adminUser.setLastname("sm");
        adminUser.setUsername("admin123");
        adminUser.setPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminRoles=new HashSet<>();

        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        userRepository.save(adminUser);

        User user=new User();
        user.setFirstname("Pradeep");
        user.setLastname("Kumar");
        user.setUsername("pradeep123");
        user.setPassword(getEncodedPassword("pradeep@pass"));
        Set<Role> userRoles=new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        userRepository.save(user);



    }

    public String getEncodedPassword(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }
}

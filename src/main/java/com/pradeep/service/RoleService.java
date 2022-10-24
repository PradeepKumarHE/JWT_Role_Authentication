package com.pradeep.service;

import com.pradeep.entity.Role;
import com.pradeep.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RolesRepository rolesRepository;

    public Role createRole(Role role){
        return rolesRepository.save(role);
    }
}

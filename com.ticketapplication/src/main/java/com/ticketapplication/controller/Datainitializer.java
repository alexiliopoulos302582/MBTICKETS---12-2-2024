package com.ticketapplication.controller;

import com.ticketapplication.entity.Role;
import com.ticketapplication.repositories.RoleRepository;
import com.ticketapplication.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Datainitializer {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initializeRoles() {

        addRoleifnotexists("USER");
        addRoleifnotexists("ADMIN");
        addRoleifnotexists("SUPERVISOR");
    }
    private void addRoleifnotexists(String rolename) {
        if (roleRepository.findByName(rolename)==null) {
            Role role = new Role();
            role.setName(rolename);
            roleRepository.save(role);
        }

    }


}

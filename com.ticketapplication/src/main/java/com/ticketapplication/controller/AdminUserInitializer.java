package com.ticketapplication.controller;

import com.ticketapplication.entity.Role;
import com.ticketapplication.entity.User;
import com.ticketapplication.repositories.RoleRepository;
import com.ticketapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component
public class AdminUserInitializer  implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initializeAdmin();

    }

    private void initializeAdmin() {
        Optional<User> adminOptional = userRepository.findByuserName("admin");
        if (adminOptional.isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole != null) {
                User adminUser = new User();
                adminUser.setUserName("admin");
                adminUser.setEmail("admin@email.com");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setEnabled("1");
                adminUser.setRole(Collections.singletonList(adminRole));
                userRepository.save(adminUser);
            }
        }
    }
}

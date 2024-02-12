package com.ticketapplication.service;

import com.ticketapplication.entity.Role;
import com.ticketapplication.entity.User;
import com.ticketapplication.repositories.RoleRepository;
import com.ticketapplication.repositories.UserRepository;
import com.ticketapplication.repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


@Component
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found"));

        if (!StringUtils.hasText(user.getPassword())) {
            throw new UsernameNotFoundException("null password");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRole())
        );

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

    String rawpassword = userRegistrationDTO.getPassword();
        if (rawpassword == null) {
            throw new IllegalArgumentException("password cannot be null");
        }
        User user = new User();
        user.setUserName(userRegistrationDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setFullName(userRegistrationDTO.getFullName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setEnabled("1");
        Role userRole = roleRepository.findByName("USER");
        if (userRole != null) {
            user.setRole(Collections.singletonList(userRole));
        } else {
            user.setRole(Collections.singletonList(new Role("USER")));
        }
        userRepository.save(user);

    }
}

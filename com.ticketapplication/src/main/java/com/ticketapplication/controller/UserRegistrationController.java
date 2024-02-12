package com.ticketapplication.controller;


import com.ticketapplication.entity.User;
import com.ticketapplication.repositories.UserRepository;
import com.ticketapplication.repositories.UserService;
import com.ticketapplication.service.UserRegistrationDTO;
import com.ticketapplication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/adduser")
public class UserRegistrationController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl userServiceimpl;

    @ModelAttribute("registrationDTO")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showaddUserForm(Model model) {
        model.addAttribute("registrationDTO", new UserRegistrationDTO());
        return "adduser";
    }

    @PostMapping
    public String adduser(@ModelAttribute("registrationDTO") UserRegistrationDTO userRegistrationDTO,
                          Model model) {
        try {
            userServiceimpl.registerUser(userRegistrationDTO);
            return "redirect:/adduser?success=true";
        } catch (Exception e) {
            return "emailerror";
        }
    }


    @GetMapping("/updateprofile/{id}")
    public String updateProfile(@PathVariable(name = "id") Integer id, Model model) {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
        }
        return "update_profile";
    }

    @PostMapping("/updateprofile")
    public String updateProfileInfo(@ModelAttribute("user") UserRegistrationDTO DTO, Model model) {

        Optional<User> usertoUpdate = userRepository.findById(DTO.getId());

        if (usertoUpdate.isPresent()) {
            User user = usertoUpdate.get();
            Optional<User> existingUserwithEmail = userRepository.findByEmail(DTO.getEmail());
            if (existingUserwithEmail.isPresent() && !existingUserwithEmail.get().getId().equals(DTO.getId())) {
                return "emailerror";
            }
            Optional<User> existingUserwithUserName = userRepository.findByuserName(DTO.getUserName());
            if (existingUserwithUserName.isPresent() &&
                    !existingUserwithUserName.get().getId().equals(DTO.getId())) {
                return "emailerror";
            }

            user.setUserName(DTO.getUserName());
            user.setFullName(DTO.getFullName());
            user.setEmail(DTO.getEmail());
            user.setPhoneNumber(DTO.getPhoneNumber());

            if (!DTO.getNewpassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(DTO.getNewpassword()));
            }
            userRepository.save(user);
            return "redirect:/adduser/updateprofile/" + DTO.getId() + "?success=#successMessage";


        } else {

        return "redirect:/emailerror";
        }


    }




}

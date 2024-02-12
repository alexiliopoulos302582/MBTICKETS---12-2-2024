package com.ticketapplication.controller;

import com.ticketapplication.entity.User;
import com.ticketapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller

public class landing {


    @Autowired
    UserRepository userRepository;

    @GetMapping("/landing")
    public String landing() {
        return "landingpage";
    }

    @GetMapping("/home")
    public String showHomepage(Model model, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUseremail = userDetails.getUsername();

        List<User> userList = userRepository.findAll();
        Integer userid = null;
        String enabledUser = "";

        for (User x : userList) {

            if (Objects.equals(x.getEmail(), loggedInUseremail)) {
                userid = x.getId();
                if (x.getEnabled().equals("0")) {
                    return "redirect:/login_disabled";
                } else {
                    enabledUser = "enabled";
                } break;
            }
        }
        model.addAttribute("userenabled", enabledUser);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("user", userList);
        model.addAttribute("userid", userid);
            return "home";

    }

    @GetMapping("/login_disabled")
    public String loginbanned() {
        return "login_disabled";
    }




}

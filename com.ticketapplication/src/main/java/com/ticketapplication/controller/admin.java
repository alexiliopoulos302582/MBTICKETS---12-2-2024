package com.ticketapplication.controller;

import com.ticketapplication.entity.Role;
import com.ticketapplication.entity.User;
import com.ticketapplication.repositories.RoleRepository;
import com.ticketapplication.repositories.UserRepository;
import com.ticketapplication.repositories.UserService;
import com.ticketapplication.service.TicketCountDTO;
import com.ticketapplication.service.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.ticketapplication.repositories.ticketRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class admin {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ticketRepository ticketRepository;

    @Autowired
    TicketCountDTO ticketCountDTO;


    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("user", userList);

        ticketRepository.truncateUserTicketCounts();
        ticketRepository.updateUserTicketCounts();

        List<TicketCountDTO> ticketCountDTOS = ticketRepository.getUserTicketCountsFromTable();
        model.addAttribute("ticketCounts", ticketCountDTOS);



        return "admin";

    }

    @GetMapping("/admin/updateuser/{id}")
    public String showModifyUserForm(@PathVariable("id") Integer id, Model model) {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> roles = new ArrayList<>(user.getRole());

            List<Integer> allRolesIds = roleRepository.findAll().stream().map(Role::getId).collect(Collectors.toList());


            model.addAttribute("user", user);
            model.addAttribute("allRoles", roles);
            model.addAttribute("databaseRoles", allRolesIds);
            return "update_user";

        } else {
            return "redirect:/admin";
        }
        }

    @PostMapping("/admin/updateuser")
    public String updateUser(@ModelAttribute("user") UserRegistrationDTO DTO,
                             @RequestParam(name = "updatedRoles", required = false) List<String> roleNames,
                             Model model) {
        try {
            Optional<User> optionalUser = userRepository.findById(DTO.getId());
            if (optionalUser.isPresent()) {
                User existinguser = optionalUser.get();
                existinguser.setUserName(DTO.getUserName());
                existinguser.setFullName(DTO.getFullName());
                existinguser.setEmail(DTO.getEmail());
                existinguser.setPhoneNumber(DTO.getPhoneNumber());
                existinguser.setEnabled(DTO.getEnabled());
                if (roleNames != null) {
                    List<Role> selectedRoles = roleNames.stream().map(
                            x->roleRepository.findByName(x)).filter(Objects::nonNull).collect(Collectors.toList());
                    existinguser.setRole(selectedRoles);
                }
                userRepository.save(existinguser);
                return "redirect:/admin";
            }
        } catch (Exception e) {e.printStackTrace();
        }
        return "redirect:/admin";
    }


    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRole().clear();
            userRepository.save(user);
        userRepository.deleteById(id);
        }
        return "redirect:/admin";

    }





}

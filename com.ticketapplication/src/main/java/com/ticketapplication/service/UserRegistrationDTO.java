package com.ticketapplication.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDTO {

    private Integer id;
    private String userName;
    private String password;
    private String newpassword;
    private String confirmpassword;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String enabled;
    private List<String> selectedRoles;

    public UserRegistrationDTO() {

        // Initialize the selectedRoles list in the constructor
        this.selectedRoles = new ArrayList<>();
    }


}

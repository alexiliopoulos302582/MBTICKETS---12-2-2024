package com.ticketapplication.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String city;
    private String clientName;
    private String email;
    private String serialNumber;
    private String address;
    private String subject;
    private String deviceModel;
    private String category;
    private String issue;
    private String afm;
    private String customerId;
    private int ticketState;
    private String solution;
    private String solutionType;
    private String assignedTo;
    private  String creationDate;


}

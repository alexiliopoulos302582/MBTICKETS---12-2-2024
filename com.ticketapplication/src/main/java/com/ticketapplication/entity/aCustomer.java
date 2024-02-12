package com.ticketapplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aCustomer")
public class aCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String aCustomerId;
    private String aCustomerName;
    private String aCustomerafm;
    private String aCustomerCity;
    private String aCustomerAddress;
    private String aCustomerEmail;
    private String aCustomerPhoneNumber;

    public aCustomer() {


    }

    public aCustomer(Integer id,
                     String aCustomerId,
                     String aCustomerName,
                     String aCustomerafm, String aCustomerCity, String aCustomerAddress, String aCustomerEmail, String aCustomerPhoneNumber) {
        this.id = id;
        this.aCustomerId = aCustomerId;
        this.aCustomerName = aCustomerName;
        this.aCustomerafm = aCustomerafm;
        this.aCustomerCity = aCustomerCity;
        this.aCustomerAddress = aCustomerAddress;
        this.aCustomerEmail = aCustomerEmail;
        this.aCustomerPhoneNumber = aCustomerPhoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getaCustomerId() {
        return aCustomerId;
    }

    public void setaCustomerId(String aCustomerId) {
        this.aCustomerId = aCustomerId;
    }

    public String getaCustomerName() {
        return aCustomerName;
    }

    public void setaCustomerName(String aCustomerName) {
        this.aCustomerName = aCustomerName;
    }

    public String getaCustomerafm() {
        return aCustomerafm;
    }

    public void setaCustomerafm(String aCustomerafm) {
        this.aCustomerafm = aCustomerafm;
    }

    public String getaCustomerCity() {
        return aCustomerCity;
    }

    public void setaCustomerCity(String aCustomerCity) {
        this.aCustomerCity = aCustomerCity;
    }

    public String getaCustomerAddress() {
        return aCustomerAddress;
    }

    public void setaCustomerAddress(String aCustomerAddress) {
        this.aCustomerAddress = aCustomerAddress;
    }

    public String getaCustomerEmail() {
        return aCustomerEmail;
    }

    public void setaCustomerEmail(String aCustomerEmail) {
        this.aCustomerEmail = aCustomerEmail;
    }

    public String getaCustomerPhoneNumber() {
        return aCustomerPhoneNumber;
    }

    public void setaCustomerPhoneNumber(String aCustomerPhoneNumber) {
        this.aCustomerPhoneNumber = aCustomerPhoneNumber;
    }
}

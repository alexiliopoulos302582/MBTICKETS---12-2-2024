package com.ticketapplication.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String serialNumber;
    private String deviceModel;
    private String branch;
    private String comments;

    public device(Integer id, String serialNumber, String deviceModel, String branch, String comments) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.deviceModel = deviceModel;
        this.branch = branch;
        this.comments = comments;

    }

    public device() {
    }
}

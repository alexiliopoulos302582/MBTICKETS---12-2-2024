package com.ticketapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/callerid")
public class callerIDcontroller {

    @Autowired
    myApplicationBean myApplicationBean;

    @GetMapping
    public ResponseEntity<String> getcallerID() {

        String callerIdPath = myApplicationBean.getCallerIdPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(callerIdPath))){
            String callerId = reader.readLine();
            if (callerId != null) {
                return ResponseEntity.ok(callerId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("number not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error reading file");
        }
    }



}

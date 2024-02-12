package com.ticketapplication.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("my")
@AllArgsConstructor
public class myApplicationBean {

    private String savePath;
    private String appURL;
    private String appUser;
    private String appPassword;
    private String callerIdPath;

    public myApplicationBean() {
   }

   public String getSavePath() {
        return savePath;
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    public String getAppURL() {
        return appURL;
    }

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getAppPassword() {
        return appPassword;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    public String getCallerIdPath() {
        return callerIdPath;
    }

    public void setCallerIdPath(String callerIdPath) {
        this.callerIdPath = callerIdPath;
    }
}

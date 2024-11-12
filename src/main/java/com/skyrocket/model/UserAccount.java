package com.skyrocket.model;

import org.apache.catalina.User;

import java.util.Date;
import java.util.UUID;

public class UserAccount {
    private UUID id;
    private String userEmail;
    private String userPw;
    private Date creationDate;

    public UserAccount(UUID id,
                       String userName,
                       String userPw,
                       String userEmail,
                       Date creationDate){

        this.id = id;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", userPw='" + userPw + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public UserAccount setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUserPw() {
        return userPw;
    }

    public UserAccount setUserPw(String userPw) {
        this.userPw = userPw;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserAccount setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public UserAccount setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public UserAccount build(){
        return this;
    }
}

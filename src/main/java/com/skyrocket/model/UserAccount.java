package com.skyrocket.model;

import java.util.Date;
import java.util.UUID;

public class UserAccount {
    private UUID id;
    private String email;
    private String password;
    private Date creationDate;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;

    public UserAccount(UUID id,
                       String email,
                       String password,
                       String sessionId) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.sessionId = sessionId;
    }

    public UUID getId() {
        return id;
    }

    public UserAccount setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAccount setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAccount setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}

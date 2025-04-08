package com.skyrocket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class SessionStore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFromAccount;
    private String sessionToken;

    public SessionStore(UUID idFromAccount, String sessionToken) {
        this.idFromAccount = idFromAccount;
        this.sessionToken = sessionToken;
    }

    public SessionStore() {

    }

    public UUID getIdFromAccount() {
        return idFromAccount;
    }

    public SessionStore setIdFromAccount(UUID idFromAccount) {
        this.idFromAccount = idFromAccount;
        return this;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public SessionStore setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public SessionStore build(){
        return this;
    }
}

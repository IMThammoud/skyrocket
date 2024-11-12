package com.skyrocket.model;

import java.util.UUID;

public class SessionStore {
    private UUID idFromAccount;
    private String sessionToken;

    public SessionStore(UUID idFromAccount, String sessionToken) {
        this.idFromAccount = idFromAccount;
        this.sessionToken = sessionToken;
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

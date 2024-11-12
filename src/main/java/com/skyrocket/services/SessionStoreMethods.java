package com.skyrocket.services;

import com.skyrocket.model.SessionStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionStoreMethods {
    SessionStore sessionStore;

    public SessionStore createSessionStore(UUID id,
                              String sessionToken){

        return sessionStore.setIdFromAccount(id)
                .setSessionToken(sessionToken)
                .build();

    }
}

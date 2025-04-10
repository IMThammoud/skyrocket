package com.skyrocket.repository;

import com.skyrocket.model.SessionStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SessionStoreRepository extends JpaRepository<SessionStore, Integer> {
    ArrayList<SessionStore> getSessionStoresBySessionToken(String sessionToken);
}

package com.skyrocket.repository;

import com.skyrocket.model.SessionStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SessionStoreRepository extends JpaRepository<SessionStore, Integer> {
    ArrayList<SessionStore> getSessionStoresBySessionToken(String sessionToken);

    SessionStore findBySessionToken(String sessionToken);

    boolean existsBySessionToken(String sessionToken);
}

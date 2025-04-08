package com.skyrocket.repository;

import com.skyrocket.model.SessionStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionStoreRepository extends JpaRepository<SessionStore, Integer> {
}

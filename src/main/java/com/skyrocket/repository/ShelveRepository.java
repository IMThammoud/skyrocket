package com.skyrocket.repository;

import com.skyrocket.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelveRepository extends JpaRepository<UserAccount, Integer> {
}

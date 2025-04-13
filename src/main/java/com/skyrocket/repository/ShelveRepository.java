package com.skyrocket.repository;

import com.skyrocket.model.Shelve;
import com.skyrocket.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ShelveRepository extends JpaRepository<Shelve, Integer> {
    List<Shelve> findByUserAccount(UserAccount userAccount);
}

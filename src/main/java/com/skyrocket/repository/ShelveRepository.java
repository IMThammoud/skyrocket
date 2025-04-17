package com.skyrocket.repository;

import com.skyrocket.model.Shelve;
import com.skyrocket.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShelveRepository extends JpaRepository<Shelve, Integer> {
    List<Shelve> findByUserAccount(UserAccount userAccount);

    Shelve findById(UUID id);
}

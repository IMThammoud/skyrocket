package com.skyrocket.repository;

import com.skyrocket.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    boolean getUserAccountByEmail(String email);

    boolean getUserAccountByPassword(String password);

    UserAccount getByEmail(String email);

    UserAccount findById(UUID id);
}

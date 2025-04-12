package com.skyrocket.repository;

import com.skyrocket.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    boolean getUserAccountByEmail(String email);

    boolean getUserAccountByPassword(String password);

    UserAccount getByEmail(String email);
}

package com.skyrocket.repository;

import com.skyrocket.model.UserAccount;
import com.skyrocket.model.UserSalts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSaltsRepository extends JpaRepository<UserSalts, Integer> {
    UserSalts getSaltByUserAccount(UserAccount userAccountId);
}

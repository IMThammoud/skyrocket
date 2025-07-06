package com.skyrocket.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserSalts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private byte[] salt;

    @JsonIdentityReference(alwaysAsId = true)  // Only serialize the ID of UserAccount
    @OneToOne
    @JoinColumn(name = "fk_user_account")
    private UserAccount userAccount;
    @CreatedDate
    private LocalDateTime createdAt;

    public UserSalts(){};

    public UserSalts( UUID id,  byte[] salt,  UserAccount userAccount, LocalDateTime createdAt) {
        this.id = id;
        this.salt = salt;
        this.userAccount = userAccount;
        this.createdAt = createdAt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

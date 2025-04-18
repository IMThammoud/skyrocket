package com.skyrocket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class SessionStore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ON LOGIN: New entries are stored so 1 UserAccount can have multiple sessionStore entries. Only 1 entry has to match
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "fk_user_account_id")
    private UserAccount userAccount;
    private String sessionToken;
    @CreatedDate
    private LocalDateTime createdAt;

    public SessionStore(UserAccount userAccount, String sessionToken) {
        this.userAccount = userAccount;
        this.sessionToken = sessionToken;
    }

    public SessionStore() {

    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public SessionStore setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public SessionStore setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public SessionStore build(){
        return this;
    }

    @Override
    public String toString() {
        return "SessionStore{" +
                "id=" + id +
                ", userAccount=" + userAccount +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}

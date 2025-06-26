package com.skyrocket.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserSalts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;
    private final SecureRandom salt;

    @JsonIdentityReference(alwaysAsId = true)  // Only serialize the ID of UserAccount
    @OneToOne
    @JoinColumn(name = "fk_user_account")
    private final UserAccount user;
    @CreatedDate
    private final OffsetDateTime createdAt;

    public UserSalts(final UUID id , final SecureRandom salt, final UserAccount user, OffsetDateTime createdAt) {
        this.id = id;
        this.salt = salt;
        this.user = user;
        this.createdAt = createdAt;
    }

    public SecureRandom getSalt() {
        return salt;
    }

    public UserAccount getUser() {
        return user;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }
}

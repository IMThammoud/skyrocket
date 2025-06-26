package com.skyrocket.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
public class UserAccount {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount")
    public List<SessionStore> sessionStore;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount")
    public List<Shelve> shelve;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserSalts salt;

    public UserAccount() {
    }

    public UserAccount(final String email, final String password, final LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UserAccount setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAccount setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAccount setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserAccount setCreatedAt(LocalDateTime creationDate) {
        this.createdAt = creationDate;
        return this;
    }

    public UserAccount build() {
        return this;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + createdAt +
                '}';
    }
}

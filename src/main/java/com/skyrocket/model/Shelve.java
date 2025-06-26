package com.skyrocket.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.skyrocket.model.articles.electronics.Notebook;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
public class Shelve {

    @JsonIdentityReference(alwaysAsId = true)  // Only serialize the ID of UserAccount
    @ManyToOne
    @JoinColumn(name = "fk_user_account")
    public UserAccount userAccount;

    @OneToMany(mappedBy = "shelve")
    List<Notebook> notebooks;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String category;

    private boolean isForServices;

    private String type;

    @CreatedDate
    private LocalDateTime createdAt;

    public Shelve() {}

    public Shelve(UUID id, String name, String category, boolean isForServices, String type, UserAccount userAccount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isForServices = isForServices;
        this.type = type;
        this.userAccount = userAccount;
    }


    public String getType() {
        return type;
    }

    public Shelve setType(String type) {
        this.type = type;
        return this;
    }


    public UUID getId() {
        return id;
    }

    public Shelve setId() {
        this.id = UUID.randomUUID();
        return this;
    }

    public String getName() {
        return name;
    }

    public Shelve setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Shelve setCategory(String category) {
        this.category = category;
        return this;
    }

    public boolean isForServices() {
        return isForServices;
    }

    public Shelve setForServices(boolean forServices) {
        this.isForServices = forServices;
        return this;
    }

    public boolean getIsForService() {
        return this.isForServices;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Shelve build() {
        return this;
    }

    @Override
    public String toString() {
        return "Shelve{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isForServices=" + isForServices +
                ", type='" + type + '\'' +
                ", userAccount=" + userAccount +
                ", notebooks=" + notebooks +
                '}';
    }

    public List<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

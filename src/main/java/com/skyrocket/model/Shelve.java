package com.skyrocket.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Shelve {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String category;
    private boolean isForServices;
    private String type;

    @ManyToOne
    @JoinColumn(name = "fk_user_account")
    public UserAccount userAccount;

    public Shelve() {

    }


    public String getType() {
        return type;
    }

    public Shelve setType(String type) {
        this.type = type;
        return this;
    }

    public Shelve(UUID id, String name, String category, boolean isForServices, String type,UserAccount userAccount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isForServices = isForServices;
        this.type = type;
        this.userAccount = userAccount;
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

    public boolean getIsForService(){
        return this.isForServices;
    }

    public Shelve setForServices(boolean forServices) {
        this.isForServices = forServices;
        return this;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Shelve build(){
        return this;
    }
}

package com.skyrocket.model;

import java.util.UUID;

public class Shelve {
    private UUID id;
    private String name;
    private String category;
    private boolean isForServices;

    private UUID fkUserAccount;

    public Shelve(UUID id, String name, String category, boolean isForServices, UUID fkUserAccount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isForServices = isForServices;
        this.fkUserAccount = fkUserAccount;
    }


    public UUID getId() {
        return id;
    }

    public Shelve setId(UUID id) {
        this.id = id;
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

    public UUID getFkUserAccount() {
        return fkUserAccount;
    }

    public void setFkUserAccount(UUID fkUserAccount) {
        this.fkUserAccount = fkUserAccount;
    }

    public Shelve build(){
        return this;
    }
}

package com.skyrocket.model;

import java.util.HashMap;
import java.util.Map;

public class RetrievedShelves {
    private String name;
    private String id;
    private String type;
    private String category;

    public RetrievedShelves(String name, String id, String type, String category) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.category = category;


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RetrievedShelves{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

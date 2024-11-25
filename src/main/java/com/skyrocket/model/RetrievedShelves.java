package com.skyrocket.model;

import java.util.HashMap;
import java.util.Map;

public class RetrievedShelves {
    private String name;
    private String id;

    public RetrievedShelves(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RetrievedShelves{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.skyrocket.model;

import java.util.UUID;

public class OfferedService {
    private UUID id;
    private String name;
    private int hoursNeeded;
    private String description;
    private double materialCost;
    private double sellingPrice;

    public OfferedService(UUID id, String name, int hoursNeeded, String description, double materialCost, double sellingPrice) {
        this.id = id;
        this.name = name;
        this.hoursNeeded = hoursNeeded;
        this.description = description;
        this.materialCost = materialCost;
        this.sellingPrice = sellingPrice;
    }

    public UUID getId() {
        return id;
    }

    public OfferedService setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferedService setName(String name) {
        this.name = name;
        return this;
    }

    public int getHoursNeeded() {
        return hoursNeeded;
    }

    public OfferedService setHoursNeeded(int hoursNeeded) {
        this.hoursNeeded = hoursNeeded;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferedService setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public OfferedService setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public double getMaterialCost() {
        return materialCost;
    }

    public OfferedService setMaterialCost(double materialCost) {
        this.materialCost = materialCost;
        return this;
    }

    public OfferedService build(){
        return this;
    }
}

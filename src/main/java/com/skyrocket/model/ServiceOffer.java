/*
This class sets the main attributes of an offered service
in german we say "Dienstleistung"
 */

package com.skyrocket.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceOffer {
    private UUID id;
    private String name;
    private int hoursNeeded;
    private String description;
    private double materialCost;
    private double sellingPrice;

    public ServiceOffer(UUID id, String name, int hoursNeeded, String description, double materialCost, double sellingPrice) {
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

    public ServiceOffer setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceOffer setName(String name) {
        this.name = name;
        return this;
    }

    public int getHoursNeeded() {
        return hoursNeeded;
    }

    public ServiceOffer setHoursNeeded(int hoursNeeded) {
        this.hoursNeeded = hoursNeeded;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ServiceOffer setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public ServiceOffer setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public double getMaterialCost() {
        return materialCost;
    }

    public ServiceOffer setMaterialCost(double materialCost) {
        this.materialCost = materialCost;
        return this;
    }

    public ServiceOffer build(){
        return this;
    }
}

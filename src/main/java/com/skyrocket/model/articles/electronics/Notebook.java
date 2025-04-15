package com.skyrocket.model.articles.electronics;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.skyrocket.Article;
import com.skyrocket.model.Shelve;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Notebook extends Article {
    // Primary_KEY is being inherited from superclass

    private String brand;
    private String modelNr;
    private String cpu;
    private int ram;
    private int storage;
    private double displaySize;
    private String operatingSystem;
    private double batteryCapacityHealth;
    private String keyboardLayout;
    private String sideNote;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "shelve_id")
    private Shelve shelve;

    public Notebook() {
        super();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private LocalDateTime createdAt;

    public Notebook(UUID id,
                    String name,
                    int amount,
                    String type,
                    String description,
                    double priceWhenBought,
                    double sellingPrice,
                    Shelve shelve,
                    String brand,
                    String modelNr,
                    String cpu,
                    int ram,
                    int storage,
                    double displaySize,
                    String operatingSystem,
                    double batteryCapacityHealth,
                    String keyboardLayout,
                    String sideNote
                    ) {
        super(id, name, amount, type, description, priceWhenBought, sellingPrice);
        this.shelve = shelve;
        this.brand = brand;
        this.modelNr = modelNr;
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.displaySize = displaySize;
        this.operatingSystem = operatingSystem;
        this.batteryCapacityHealth = batteryCapacityHealth;
        this.keyboardLayout = keyboardLayout;
        this.sideNote = sideNote;



    }

    // to make the superclass ID primary_key
    /*@Id
    @Override
    public UUID getId() {
        return super.getId();
    }
    */

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelNr() {
        return modelNr;
    }

    public void setModelNr(String modelNr) {
        this.modelNr = modelNr;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(double displaySize) {
        this.displaySize = displaySize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public double getBatteryCapacityHealth() {
        return batteryCapacityHealth;
    }

    public void setBatteryCapacityHealth(double batteryCapacityHealth) {
        this.batteryCapacityHealth = batteryCapacityHealth;
    }

    public String getKeyboardLayout() {
        return keyboardLayout;
    }

    public void setKeyboardLayout(String keyboardLayout) {
        this.keyboardLayout = keyboardLayout;
    }

    public String getSideNote() {
        return sideNote;
    }

    public void setSideNote(String sideNote) {
        this.sideNote = sideNote;
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "brand='" + brand + '\'' +
                ", modelNr='" + modelNr + '\'' +
                ", cpu='" + cpu + '\'' +
                ", ram=" + ram +
                ", storage=" + storage +
                ", displaySize=" + displaySize +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", batteryCapacityHealth=" + batteryCapacityHealth +
                ", keyboardLayout='" + keyboardLayout + '\'' +
                ", sideNote='" + sideNote + '\'' +
                ", shelve=" + shelve.getId() +
                ", createdAt=" + createdAt +
                '}';
    }
}

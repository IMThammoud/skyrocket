package com.skyrocket.model.articles;

import com.skyrocket.Article;

import java.util.UUID;

public class Notebook extends Article {
    private String manufacturer;
    private String modelNr;
    private String cpu;
    private int ram;
    private int storage;
    private double displaySize;
    private String operatingSystem;
    private double batteryCapacityHealth;
    private String keyboardLayout;
    private String sideNote;

    public Notebook(UUID id,
                    String name,
                    int amount,
                    String type,
                    String description,
                    double priceWhenBought,
                    double sellingPrice,
                    String manufacturer,
                    String modelNr,
                    String cpu,
                    int ram,
                    int storage,
                    double displaySize,
                    String keyboardLayout,
                    String sideNote,
                    UUID shelveIdAsForeinKey
                    ) {
        super(id, name, amount, type, description, priceWhenBought, sellingPrice, shelveIdAsForeinKey);
        this.manufacturer = manufacturer;
        this.modelNr = modelNr;
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.displaySize = displaySize;
        this.keyboardLayout = keyboardLayout;
        this.sideNote = sideNote;



    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
}

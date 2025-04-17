package com.skyrocket.utilityClasses;

import com.skyrocket.Article;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.articles.electronics.Notebook;
import com.skyrocket.services.ConvertNotebookListForPDF;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FilteredNotebookListForPDF implements ConvertNotebookListForPDF {
    private String name;
    private int amount;
    private String type;
    private double priceWhenBought;
    private double sellingPrice;
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

    public FilteredNotebookListForPDF() {}

    // Constructor For listing articles for sale with sellingPrice
    public FilteredNotebookListForPDF(
                    String name,
                    int amount,
                    String type,
                    double sellingPrice,
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
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.sellingPrice = sellingPrice;
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

    // This should be used for the rendered Articles on shelve dashboard
    // Converting the Notebooklist parameter to a List that has filtered Notebooks (some columns removed like id, createdAt or similar)
    // There will be a new filteredNotebook for each notebook in the parameter Notebooklist and then
    // the new created filteredNotebooks will be added to a List and return that List for PDF Activities or for frontend Rendering:D
    @Override
    public List<FilteredNotebookListForPDF> filterOutNotUsedColumnsAndCreateNewListForPDF(List<Notebook> notebookList) {
        List<FilteredNotebookListForPDF> filteredNotebookListForPDF = new ArrayList<>();
        for (Notebook notebook : notebookList) {
            // I only get the necessary Fields here for the PDF
            filteredNotebookListForPDF.add( new FilteredNotebookListForPDF(
                    notebook.getName(),
                    notebook.getAmount(),
                    notebook.getType(),
                    notebook.getSellingPrice(),
                    notebook.getBrand(),
                    notebook.getModelNr(),
                    notebook.getCpu(),
                    notebook.getRam(),
                    notebook.getStorage(),
                    notebook.getDisplaySize(),
                    notebook.getOperatingSystem(),
                    notebook.getBatteryCapacityHealth(),
                    notebook.getKeyboardLayout(),
                    notebook.getSideNote()
                )
            );


        }
        return filteredNotebookListForPDF;
    }

    public String getSideNote() {
        return sideNote;
    }

    public void setSideNote(String sideNote) {
        this.sideNote = sideNote;
    }

    public String getKeyboardLayout() {
        return keyboardLayout;
    }

    public void setKeyboardLayout(String keyboardLayout) {
        this.keyboardLayout = keyboardLayout;
    }

    public double getBatteryCapacityHealth() {
        return batteryCapacityHealth;
    }

    public void setBatteryCapacityHealth(double batteryCapacityHealth) {
        this.batteryCapacityHealth = batteryCapacityHealth;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public double getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(double displaySize) {
        this.displaySize = displaySize;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getModelNr() {
        return modelNr;
    }

    public void setModelNr(String modelNr) {
        this.modelNr = modelNr;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getPriceWhenBought() {
        return priceWhenBought;
    }

    public void setPriceWhenBought(double priceWhenBought) {
        this.priceWhenBought = priceWhenBought;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FilteredNotebookListForPDF{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", priceWhenBought=" + priceWhenBought +
                ", sellingPrice=" + sellingPrice +
                ", brand='" + brand + '\'' +
                ", modelNr='" + modelNr + '\'' +
                ", cpu='" + cpu + '\'' +
                ", ram=" + ram +
                ", storage=" + storage +
                ", displaySize=" + displaySize +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", batteryCapacityHealth=" + batteryCapacityHealth +
                ", keyboardLayout='" + keyboardLayout + '\'' +
                ", sideNote='" + sideNote + '\'' +
                '}';
    }
}

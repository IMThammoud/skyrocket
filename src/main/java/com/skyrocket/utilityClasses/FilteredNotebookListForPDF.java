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

    // Constructor For listing articles for sale with sellingPrice
    public FilteredNotebookListForPDF(
                    String name,
                    int amount,
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
}

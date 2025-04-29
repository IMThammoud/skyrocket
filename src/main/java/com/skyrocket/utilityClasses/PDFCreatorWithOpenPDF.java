package com.skyrocket.utilityClasses;

// This class need overhaul with generic methods that consume generic types that extend "Article".
// I should then extract the amount of fields of an object in the list to determine the amount of columns
// needed for the pdf. This should be done with 2 seperate Methods.


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.articles.electronics.Notebook;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

public class PDFCreatorWithOpenPDF {
    private Document document;
    private PdfWriter pdfWriter;
    private PdfPTable table;
    private Paragraph headerShelveName;
    private File file;

    public PDFCreatorWithOpenPDF(int amountOfColumns) throws FileNotFoundException {
        this.document = new Document();
        // This File will be generated, written to and returned by the createAndReturnPDF() Method
        this.file = new File("pdf/"+UUID.randomUUID()+".pdf");
        this.pdfWriter = PdfWriter.getInstance(this.document, new FileOutputStream(file));
        this.table = new PdfPTable(amountOfColumns);
    }

    public File createAndReturnPDFForNotebook(List<Notebook> notebooks, Shelve shelve) {
       this.table.setWidthPercentage(100);
       this.headerShelveName = new Paragraph(shelve.getName());
        this.document.setPageSize(PageSize.A4.rotate()); // gives you ~842pt width instead of 595pt
        this.document.open();
        this.document.add(headerShelveName);
        this.document.add(new Paragraph("  "));  // Empty Paragraph to create space between table and shelve.name

        float[] columnWidths = {
                1.0f,  // Amount
                1.0f,  // Brand
                1.5f,  // Name
                1.5f,  // Modelnumber
                0.8f,  // Type
                1.1f,  // CPU
                0.6f,  // RAM
                0.8f,  // Storage
                1.0f,  // Display Size
                1.3f,  // Operating System
                0.6f,  // Keyboard
                0.8f,  // Battery
                1.2f,  // Note
                1.0f   // Selling Price
        };
        table.setWidths(columnWidths);

        FilteredNotebookForPDF filteredNotebookForPDF = new FilteredNotebookForPDF();
        List<FilteredNotebookForPDF> filteredNotebookListForPDF = filteredNotebookForPDF.convertNotebookListForPDF(notebooks);
        table.setHeaderRows(1);
        // For every column i add a cell to the pdf template with the right name of the column element ( i defined those)
        for(String column : filteredNotebookForPDF.getColumnsForTablePDF()) {
            System.out.println(column);
            table.addCell(new PdfPCell(new Phrase(column, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9f)))).setBackgroundColor(Color.orange);
        }

            // Create a row for every Notebook in the Filtered Notebook List
            for (FilteredNotebookForPDF filteredNotebook : filteredNotebookListForPDF) {
                table.addCell(String.valueOf(filteredNotebook.getAmount()));
                table.addCell(filteredNotebook.getBrand());
                table.addCell(filteredNotebook.getName());
                table.addCell(filteredNotebook.getModelNr());
                table.addCell(filteredNotebook.getType());
                table.addCell(filteredNotebook.getCpu());
                table.addCell(String.valueOf(filteredNotebook.getRam()));
                table.addCell(String.valueOf(filteredNotebook.getStorage()));
                table.addCell(String.valueOf(filteredNotebook.getDisplaySize()));
                table.addCell(filteredNotebook.getOperatingSystem());
                table.addCell(filteredNotebook.getKeyboardLayout());
                table.addCell(String.valueOf(filteredNotebook.getBatteryCapacityHealth()));
                table.addCell(new PdfPCell(new Phrase(filteredNotebook.getSideNote(), FontFactory.getFont(FontFactory.HELVETICA, 9f))));
                //table.addCell(filteredNotebook.getSideNote());
                table.addCell(String.valueOf(filteredNotebook.getSellingPrice()));
            }

        System.out.println("Columns: " + filteredNotebookForPDF.getColumnsForTablePDF());
        System.out.println("Notebooks: " + notebooks);
        System.out.println(table.getNumberOfColumns());


        System.out.println(shelve.getName());
        this.document.add(table);
        this.document.close();
        //pdfWriter.close();

        return file;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public PdfWriter getPdfWriter() {
        return pdfWriter;
    }

    public void setPdfWriter(PdfWriter pdfWriter) {
        this.pdfWriter = pdfWriter;
    }

    public PdfPTable getTable() {
        return table;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
    }

    public Paragraph getHeaderShelveName() {
        return headerShelveName;
    }

    public void setHeaderShelveName(Paragraph headerShelveName) {
        this.headerShelveName = headerShelveName;
    }
}

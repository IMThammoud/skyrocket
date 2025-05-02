package com.skyrocket.utilityClasses;

// This class need overhaul with generic methods that consume generic types that extend "Article".
// I should then extract the amount of fields of an object in the list to determine the amount of columns
// needed for the pdf. This should be done with 2 seperate Methods.


import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.TextField;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.articles.electronics.Notebook;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
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

    public File createInvoiceFreeModePDF(Map<String, String > invoiceInfo) {
        PdfPTable tableForBillerAndCustomerInfo = new PdfPTable(2);
        PdfPCell billerCell = new PdfPCell();
        billerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        billerCell.setBackgroundColor(Color.lightGray);
        PdfPCell toBeBilledCell = new PdfPCell();
        toBeBilledCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        toBeBilledCell.setBackgroundColor(Color.lightGray);
        Paragraph headerForFreeModeInvoice = new Paragraph("- Invoice - ");
        headerForFreeModeInvoice.setAlignment(Element.ALIGN_CENTER);
        headerForFreeModeInvoice.setFont(FontFactory.getFont(FontFactory.HELVETICA, 20));
        Paragraph date  = new Paragraph(invoiceInfo.get("date"));
        date.setAlignment(Paragraph.ALIGN_RIGHT);
        ////////////////////////////////////////////////////////////////////
        // Invoice Creator Info
        Paragraph nameCreator = new Paragraph(invoiceInfo.get("name_creator"));
        Paragraph addressCreator = new Paragraph(invoiceInfo.get("address_creator"));
        Paragraph zipCodeCreator = new Paragraph(invoiceInfo.get("zip_code_creator"));
        Paragraph cityCreator = new Paragraph(invoiceInfo.get("city_creator"));
        Paragraph telCreator = new Paragraph(invoiceInfo.get("tel_creator"));
        ////////////////////////////////////////////////////////////////////
        // Customer Info
        Paragraph nameCustomer = new Paragraph(invoiceInfo.get("name_customer"));
        Paragraph addressCustomer = new Paragraph(invoiceInfo.get("address_customer"));
        Paragraph zipCodeCustomer = new Paragraph(invoiceInfo.get("zip_code_customer"));
        Paragraph cityCustomer = new Paragraph(invoiceInfo.get("city_customer"));
        Paragraph telCustomer = new Paragraph(invoiceInfo.get("tel_customer"));
        ////////////////////////////////////////////////////////////////////
        // Article / Service Offering info
        Paragraph invoiceId = new Paragraph(invoiceInfo.get("invoice_id"));
        Paragraph textAreaArticle = new Paragraph(invoiceInfo.get("text_area_article"));
        Paragraph articlePrice = new Paragraph(invoiceInfo.get("price"));
        Paragraph taxPercentage = new Paragraph(invoiceInfo.get("tax_percentage"));
        /////////////////////////////////////////////////////////////////////
        // Open Document and add fields to it and close it at the end.
        this.document.open();
        this.document.add(headerForFreeModeInvoice);
        this.document.add(Chunk.NEWLINE);
        this.document.add(date);
        this.document.add(Chunk.NEWLINE);
        /// /////////////////////////////////////////////////////////////////
        // Adding paragraphs to cells and cells to table and table to document. In this Order.
        // Adding Invoice Creator Info to Document
        billerCell.addElement(nameCreator);
        toBeBilledCell.addElement(nameCustomer);
        tableForBillerAndCustomerInfo.addCell(billerCell);
        tableForBillerAndCustomerInfo.addCell(toBeBilledCell);
        this.document.add(tableForBillerAndCustomerInfo);
        this.document.add(new Paragraph("Biller:"));
        this.document.add(nameCreator);
        this.document.add(addressCreator);
        this.document.add(zipCodeCreator);
        this.document.add(cityCreator);
        this.document.add(telCreator);
        this.document.add(Chunk.NEWLINE);
        /// //////////////////////////////////////////////////////////////////
        // Adding customer Info ot Document
        this.document.add(new Paragraph("Bill goes to: "));
        this.document.add(nameCustomer);
        this.document.add(addressCustomer);
        this.document.add(zipCodeCustomer);
        this.document.add(cityCustomer);
        this.document.add(telCustomer);
        this.document.add(Chunk.NEWLINE);
        this.document.add(Chunk.NEWLINE);
        /// ///////////////////////////////////////////////////////////////////
        // Adding Article / Service Offering Info
        this.document.add(new Paragraph("Invoice ID: "+ invoiceId));
        this.document.add(Chunk.NEWLINE);
        this.document.add(new Paragraph("Article / Service: "));
        this.document.add(textAreaArticle);
        this.document.add(Chunk.NEWLINE);
        /// ///////////////////////////////////////////////////////////////////
        // Parse Price and make calculation with tax percentage
        double parsedFullPrice = Double.parseDouble(invoiceInfo.get("price"));
        double parsedTaxRate = Double.parseDouble(invoiceInfo.get("tax_percentage"));
        double parsedTaxPercentage = Double.parseDouble(invoiceInfo.get("tax_percentage"));
        double priceBeforeTax =   parsedFullPrice - Double.parseDouble(invoiceInfo.get("price")) * (parsedTaxPercentage / 100);
        double taxResult = Double.parseDouble(invoiceInfo.get("price")) * (parsedTaxPercentage / 100);
        this.document.add(new Paragraph("Tax Percentage:  " + String.format("%.2f",parsedTaxRate) + "%" ));
        this.document.add(new Paragraph("Price before Tax:  " + String.format("%.2f",priceBeforeTax) + "€"));
        this.document.add(new Paragraph("Tax Result:  " + String.format("%.2f",taxResult) + "€" ));
        this.document.add(new Paragraph("Price after Tax:  " + String.format("%.2f",parsedFullPrice) + "€"));
        this.document.close();

        return this.file;
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

package com.skyrocket.services;

// This class need overhaul with generic methods that consume generic types that extend "Article".
// I should then extract the amount of fields of an object in the list to determine the amount of columns
// needed for the pdf. This should be done with 2 seperate Methods.

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.skyrocket.model.non_entities.FilteredNotebookForPDF;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.articles.electronics.Notebook;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PDFCreatorWithOpenPDF {
    private Document document;
    private PdfWriter pdfWriter;
    private PdfPTable table;
    private Paragraph headerShelveName;
    private File file;

    public PDFCreatorWithOpenPDF() throws FileNotFoundException {
        this.document = new Document();
        // This File will be generated, written to and returned by the createAndReturnPDF() Method
        this.file = new File("pdf/"+UUID.randomUUID()+".pdf");
        this.pdfWriter = PdfWriter.getInstance(this.document, new FileOutputStream(file));
    }

    public File createInvoiceFreeModePDF(Map<String, String> invoiceInfo) throws IOException {
        /*
        Paragraph headerForFreeModeInvoice = new Paragraph("- Invoice - ");
        // Table for biller und customer info
        PdfPTable tableForBillerAndCustomerInfo = new PdfPTable(2);
        // Table for Article or Service
        PdfPTable tableForArticleOrService = new PdfPTable(1);
        PdfPCell articleCell = new PdfPCell();
        articleCell.setBackgroundColor(Color.lightGray);
        PdfPCell billerCell = new PdfPCell();
        billerCell.setBorder(Rectangle.NO_BORDER);
        billerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell toBeBilledCell = new PdfPCell();
        toBeBilledCell.setBorder(Rectangle.NO_BORDER);
        toBeBilledCell.setHorizontalAlignment(Element.ALIGN_LEFT);
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
        /// ///////////////////////////////////////////////////////////////
        // Add invoice creator to left cell
        billerCell.addElement(new Paragraph("Biller: "));
        billerCell.addElement(nameCreator);
        billerCell.addElement(addressCreator);
        billerCell.addElement(zipCodeCreator);
        billerCell.addElement(cityCreator);
        billerCell.addElement(telCreator);
        ////////////////////////////////////////////////////////////////////
        // Customer Info
        Paragraph nameCustomer = new Paragraph(invoiceInfo.get("name_customer"));
        Paragraph addressCustomer = new Paragraph(invoiceInfo.get("address_customer"));
        Paragraph zipCodeCustomer = new Paragraph(invoiceInfo.get("zip_code_customer"));
        Paragraph cityCustomer = new Paragraph(invoiceInfo.get("city_customer"));
        Paragraph telCustomer = new Paragraph(invoiceInfo.get("tel_customer"));
        /// ////////////////////////////////////////////////////////////////
        // Add Customer info to the right cell
        toBeBilledCell.addElement(new Paragraph("Customer: "));
        toBeBilledCell.addElement(nameCustomer);
        toBeBilledCell.addElement(addressCustomer);
        toBeBilledCell.addElement(zipCodeCustomer);
        toBeBilledCell.addElement(cityCustomer);
        toBeBilledCell.addElement(telCustomer);
        /// ///////////////////////////////////////////////////////////////
        // Add cells to billercells and customer table.
        tableForBillerAndCustomerInfo.addCell(billerCell);
        tableForBillerAndCustomerInfo.addCell(toBeBilledCell);
        ////////////////////////////////////////////////////////////////////
        // Article / Service Offering info
        Paragraph invoiceId = new Paragraph(invoiceInfo.get("invoice_id"));
        Paragraph articleHeaderText = new Paragraph("Article / Service");
        Paragraph textAreaArticle = new Paragraph(invoiceInfo.get("text_area_article"));
        Paragraph articlePrice = new Paragraph(invoiceInfo.get("price"));
        Paragraph taxPercentage = new Paragraph(invoiceInfo.get("tax_percentage"));
        /// ///////////////////////////////////////////////////////////////
        // Calculating Taxes and price.
        double parsedFullPrice = Double.parseDouble(invoiceInfo.get("price"));
        double parsedTaxRate = Double.parseDouble(invoiceInfo.get("tax_percentage"));
        double parsedTaxPercentage = Double.parseDouble(invoiceInfo.get("tax_percentage"));
        double priceBeforeTax =   parsedFullPrice - Double.parseDouble(invoiceInfo.get("price")) * (parsedTaxPercentage / 100);
        double taxResult = Double.parseDouble(invoiceInfo.get("price")) * (parsedTaxPercentage / 100);
        /// ///////////////////////////////////////////////////////////////
        // Adding taxes and prices to the article cell.
        articleCell.addElement(textAreaArticle);
        articleCell.addElement(new Paragraph("Tax Percentage:  " + String.format("%.2f",parsedTaxRate) + "%" ));
        articleCell.addElement(new Paragraph("Price before Tax:  " + String.format("%.2f",priceBeforeTax) + "€"));
        articleCell.addElement(new Paragraph("Tax Result:  " + String.format("%.2f",taxResult) + "€" ));
        articleCell.addElement(new Paragraph("Price after Tax:  " + String.format("%.2f",parsedFullPrice) + "€"));
        /// ///////////////////////////////////////////////////////////////
        // Adding article Cell to article / service table
        tableForArticleOrService.addCell(articleCell);
        /////////////////////////////////////////////////////////////////////
        // Open Document and add fields to it and close it at the end.
        this.document.open();
        this.document.add(headerForFreeModeInvoice);
        this.document.add(Chunk.NEWLINE);
        this.document.add(date);
        this.document.add(Chunk.NEWLINE);
        /// /////////////////////////////////////////////////////////////////
        // Adding billdercell and customerCell to the table and adding table to document.
        // Adding Invoice Creator Info to Document
        this.document.add(tableForBillerAndCustomerInfo);
        this.document.add(Chunk.NEWLINE);
        this.document.add(Chunk.NEWLINE);
        /// ///////////////////////////////////////////////////////////////////
        // Adding Article / Service Offering Info
        this.document.add(new Paragraph("Invoice ID: "+ invoiceId));
        this.document.add(Chunk.NEWLINE);
        this.document.add(Chunk.NEWLINE);
        this.document.add(Chunk.NEWLINE);
        this.document.add(tableForArticleOrService);
        this.document.add(Chunk.NEWLINE);
        /// ///////////////////////////////////////////////////////////////////
        // Close document and return file

        */
        this.document.open();

        // Add logo
        Image logo = Image.getInstance("invoice_demo_icon.jpeg");
        logo.scaleToFit(60, 60);
        logo.setAlignment(Image.ALIGN_LEFT);
        document.add(logo);

        // Add invoice title and date
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3, 2});

        PdfPCell titleCell = new PdfPCell(new Phrase("INVOICE", new Font(Font.HELVETICA, 18, Font.BOLD)));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_TOP);
        headerTable.addCell(titleCell);

        PdfPCell dateCell = new PdfPCell(new Phrase("Date: " + invoiceInfo.get("date") + "\nInvoice ID: " + invoiceInfo.get("invoice_id")));
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(dateCell);
        document.add(headerTable);

        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));

        // Add biller and customer info
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingBefore(10f);

        PdfPCell billerCell = new PdfPCell();
        billerCell.setBorder(Rectangle.NO_BORDER);
        billerCell.addElement(new Paragraph("Biller:"));
        billerCell.addElement(new Paragraph(invoiceInfo.get("name_creator")));
        billerCell.addElement(new Paragraph(invoiceInfo.get("address_creator")));
        billerCell.addElement(new Paragraph(invoiceInfo.get("zip_code_creator") + " " + invoiceInfo.get("city_creator")));
        billerCell.addElement(new Paragraph("Tel: " + invoiceInfo.get("tel_creator")));

        PdfPCell customerCell = new PdfPCell();
        customerCell.setBorder(Rectangle.NO_BORDER);
        customerCell.addElement(new Paragraph("To:"));
        customerCell.addElement(new Paragraph(invoiceInfo.get("name_customer")));
        customerCell.addElement(new Paragraph(invoiceInfo.get("address_customer")));
        customerCell.addElement(new Paragraph(invoiceInfo.get("zip_code_customer") + " " + invoiceInfo.get("city_customer")));
        customerCell.addElement(new Paragraph("Tel: " + invoiceInfo.get("tel_customer")));

        infoTable.addCell(billerCell);
        infoTable.addCell(customerCell);
        document.add(infoTable);

        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));

        // Artikel / Dienstleistung
        PdfPTable articleTable = new PdfPTable(4);
        articleTable.setWidthPercentage(100);
        articleTable.setSpacingBefore(20f);
        articleTable.setWidths(new float[]{4, 2, 2, 2});

        articleTable.addCell(new PdfPCell(new Phrase("Article / Service")));
        articleTable.addCell(new PdfPCell(new Phrase("Price before Tax")));
        articleTable.addCell(new PdfPCell(new Phrase("Tax sum")));
        articleTable.addCell(new PdfPCell(new Phrase("Price after Tax")));

        double priceGross = Double.parseDouble(invoiceInfo.get("price"));
        double taxRate = Double.parseDouble(invoiceInfo.get("tax_percentage"));
        double taxAmount = priceGross * (taxRate / (100 + taxRate));
        double priceNet = priceGross - taxAmount;

        articleTable.addCell(new PdfPCell(new Phrase(invoiceInfo.get("text_area_article"))));
        articleTable.addCell(new PdfPCell(new Phrase(String.format("%.2f €", priceNet))));
        articleTable.addCell(new PdfPCell(new Phrase(String.format("%.2f €", taxAmount))));
        articleTable.addCell(new PdfPCell(new Phrase(String.format("%.2f €", priceGross))));

        document.add(articleTable);


        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("Thank you for your Order!"));

        document.close();

        return this.file;
    }

    public File createAndReturnPDFForNotebook(List<Notebook> notebooks, Shelve shelve) {
        FilteredNotebookForPDF filteredNotebookForPDF = new FilteredNotebookForPDF();
        List<FilteredNotebookForPDF> filteredNotebookListForPDF = filteredNotebookForPDF.convertNotebookListForPDF(notebooks);
        this.table = new PdfPTable(filteredNotebookForPDF.getColumnsForTablePDF().size());
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

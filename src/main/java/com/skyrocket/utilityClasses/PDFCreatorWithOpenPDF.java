package com.skyrocket.utilityClasses;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.skyrocket.Article;
import com.skyrocket.model.articles.electronics.Notebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PDFCreatorWithOpenPDF {
    private Document document;
    private PdfWriter pdfWriter;
    private PdfPTable table;
    private Paragraph testingParagraph = new Paragraph("Testing OpenPDF: This is a Paragraph");
    private File file;

    public PDFCreatorWithOpenPDF(int amountOfColumns) throws FileNotFoundException {
        this.document = new Document();
        // This File will be generated, written to and returned by the createAndReturnPDF() Method
        this.file = new File("pdf/"+UUID.randomUUID()+".pdf");
        this.pdfWriter = PdfWriter.getInstance(this.document, new FileOutputStream(file));
        this.table = new PdfPTable(amountOfColumns);
    }

    public File createAndReturnPDFForNotebook(List<Notebook> notebooks) {

       this.table.setWidthPercentage(100);
        this.document.setPageSize(PageSize.A4.rotate()); // gives you ~842pt width instead of 595pt
        this.document.newPage();
        this.document.open();
        this.document.newPage();
         this.document.add(this.testingParagraph);
        float[] columnWidths = {
                0.6f,  // Amount
                1.2f,  // Brand
                1.5f,  // Name
                1.5f,  // Modelnumber
                1.0f,  // Type
                1.2f,  // CPU
                0.8f,  // RAM
                1.0f,  // Storage
                1.0f,  // Display Size
                1.3f,  // Operating System
                0.9f,  // Keyboard
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
            table.addCell(new PdfPCell(new Phrase(column, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6f))));
        }

        /*for (FilteredNotebookForPDF filteredNotebook : filteredNotebookListForPDF) {
            table.addCell(filteredNotebookForPDF.getBrand());
            System.out.println(table.getRow(0).toString());
        }*/

        System.out.println("Columns: " + filteredNotebookForPDF.getColumnsForTablePDF().toString());
        System.out.println("Notebooks: " + notebooks.toString());
        System.out.println(table.getNumberOfColumns());

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

    public Paragraph getTestingParagraph() {
        return testingParagraph;
    }

    public void setTestingParagraph(Paragraph testingParagraph) {
        this.testingParagraph = testingParagraph;
    }
}

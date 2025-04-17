package com.skyrocket.utilityClasses;

import com.lowagie.text.Document;
import com.lowagie.text.Header;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public File createAndReturnPDF() {

        this.document.open();
        this.document.add(this.testingParagraph);
        this.document.add(this.table);

        this.document.close();
        pdfWriter.close();

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

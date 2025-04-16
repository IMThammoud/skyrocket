package com.skyrocket.services;

import com.skyrocket.model.articles.electronics.Notebook;
import com.skyrocket.utilityClasses.FilteredNotebookListForPDF;

import java.util.List;

public interface ConvertNotebookListForPDF {

    // This will be implemented by a class that creates a new List without
    // unnecessary Fields like the id or createdAt attributes so i can render them easily on the PDF.
    public List<FilteredNotebookListForPDF> filterOutNotUsedColumnsAndCreateNewListForPDF(List<Notebook> notebookList);
}

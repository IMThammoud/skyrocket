package com.skyrocket.services;

import com.skyrocket.model.articles.electronics.Notebook;
import com.skyrocket.utilityClasses.FilteredNotebookListForPDF;

import java.util.List;

public interface ConvertNotebookListForPDF {
    public List<FilteredNotebookListForPDF> convertNotebookListForPDF(List<Notebook> notebookList);
}

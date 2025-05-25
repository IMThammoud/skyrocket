package com.skyrocket.services;

import com.skyrocket.model.articles.electronics.Notebook;
import com.skyrocket.model.FilteredNotebookForPDF;

import java.util.List;

public interface ConvertNotebookListForPDF {
    public List<FilteredNotebookForPDF> convertNotebookListForPDF(List<Notebook> notebookList);
}

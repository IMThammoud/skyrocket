package com.skyrocket.model.non_entities;

import com.skyrocket.model.articles.electronics.Notebook;

import java.util.List;

public interface ConvertNotebookListForPDF {
    List<FilteredNotebookForPDF> convertNotebookListForPDF(List<Notebook> notebookList);
}

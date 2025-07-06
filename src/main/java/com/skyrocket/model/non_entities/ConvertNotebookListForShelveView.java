package com.skyrocket.model.non_entities;

import com.skyrocket.model.articles.electronics.Notebook;

import java.util.List;

public interface ConvertNotebookListForShelveView {

    // This will be implemented by a class that creates a new List without
    // unnecessary Fields like the id or createdAt attributes so i can render them easily on the PDF.
     List<FilteredNotebookForShelveView> filterOutNotUsedColumnsAndCreateNewListForShelveViewDashboard(List<Notebook> notebookList);
}

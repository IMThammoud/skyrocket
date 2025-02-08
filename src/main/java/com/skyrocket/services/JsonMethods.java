// Class to put ResultSets of SQL Queries into JSON Strings
// This externalizes JSON operations so they dont happen in SQL Methods.

package com.skyrocket.services;

import com.google.gson.Gson;
import com.skyrocket.Article;
import com.skyrocket.model.articles.Notebook;

import java.util.ArrayList;

public class JsonMethods {
    Gson gson = new Gson();
    public JsonMethods() {};

    public String StringifyOneArticle(Article article) {
        return gson.toJson(article);
    }

    // Return list of Articles
    public String StringifyListOfNotebooks(ArrayList<Notebook> listOfArticles) {
        return gson.toJson(listOfArticles);
    }
}

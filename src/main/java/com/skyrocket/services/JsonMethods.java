// Class to put ResultSets of SQL Queries into JSON Strings
// This externalizes JSON operations so they dont happen in SQL Methods.

package com.skyrocket.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyrocket.Article;
import com.skyrocket.model.articles.Notebook;

import java.util.ArrayList;

public class JsonMethods {
    ObjectMapper objectMapper = new ObjectMapper();

    public String StringifyOneArticle(Article article) throws JsonProcessingException {
        return objectMapper.writeValueAsString(article);
    }

    // Return list of Articles
    public String StringifyListOfNotebooks(ArrayList<Notebook> listOfArticles) throws JsonProcessingException {
        return objectMapper.writeValueAsString(listOfArticles);
    }
}

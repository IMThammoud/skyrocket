// Maybe this class helps with serializing when
// sending Requests with Javas Built in HTTP libs

package com.skyrocket.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skyrocket.Article;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.articles.electronics.Notebook;

import java.util.ArrayList;
import java.util.List;

public class JsonMethods {
    ObjectMapper objectMapper = new ObjectMapper();

    public String StringifyShelves(List<Shelve> shelves) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Optional: Make date output human-readable
        return objectMapper.writeValueAsString(shelves);
    }

    public String StringifyOneArticle(Article article) throws JsonProcessingException {
        return objectMapper.writeValueAsString(article);
    }

    // Return list of Articles
    public String StringifyListOfNotebooks(ArrayList<Notebook> listOfArticles) throws JsonProcessingException {
        return objectMapper.writeValueAsString(listOfArticles);
    }
}

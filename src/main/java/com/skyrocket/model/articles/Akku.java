package com.skyrocket.model.articles;

import java.util.UUID;

import com.skyrocket.Article;

public class Akku extends Article {
    
    public Akku(UUID id,
                    String name,
                    int amount,
                    String type,
                    String description,
                    double priceWhenBought,
                    double sellingPrice){
        super(id, name, amount, type, description, priceWhenBought, sellingPrice);
        

    }
}

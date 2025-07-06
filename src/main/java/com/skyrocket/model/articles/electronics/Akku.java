package com.skyrocket.model.articles.electronics;

import com.skyrocket.model.Article;

import java.util.UUID;

public class Akku extends Article {

    public Akku(UUID id,
                String name,
                int amount,
                String type,
                String description,
                double priceWhenBought,
                double sellingPrice) {
        super(id, name, amount, type, description, priceWhenBought, sellingPrice);


    }
}

package com.skyrocket.services;

import com.skyrocket.Article;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArticleMethods {
    private Article article;

    public Article createArticle(UUID id,
                                 String name,
                                 int amount,
                                 String type,
                                 String description,
                                 double priceWhenBought,
                                 double sellingPrice){
        return article.setId(id)
                .setName(name)
                .setAmount(amount)
                .setType(type)
                .setDescription(description)
                .setPriceWhenBought(priceWhenBought)
                .setSellingPrice(sellingPrice)
                .build();


    }
}

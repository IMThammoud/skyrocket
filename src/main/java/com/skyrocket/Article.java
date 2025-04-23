/*
Templates for Article subclasses like Notebooks or Smartphones
have all Attributes from this abstract class.
 */

package com.skyrocket;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int amount;
    private String type;
    private String description;
    private double priceWhenBought;
    private double sellingPrice;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Article() {

    }

    public Article(UUID id,
                   String name,
                   int amount,
                   String type,
                   String description,
                   double priceWhenBought,
                   double sellingPrice) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.priceWhenBought = priceWhenBought;
        this.sellingPrice = sellingPrice;
    }

    public UUID getId() {
        return id;
    }

    public Article setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Article setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Article setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getType() {
        return type;
    }

    public Article setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Article setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPriceWhenBought() {
        return priceWhenBought;
    }

    public Article setPriceWhenBought(double priceWhenBought) {
        this.priceWhenBought = priceWhenBought;
        return this;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public Article setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public Article build(){
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.bullish.test.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Product {

    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private double price;

    @Getter
    @Setter
    private double discountedPrice;

    private Set<DiscountDeal> discountDealSet;

    public Product(String name, double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.discountedPrice = this.price;
        discountDealSet = new HashSet<>();
    }

    public void addDiscount(DiscountDeal discountDeal) {
        discountDealSet.add(discountDeal);
    }

    public Set<DiscountDeal> getDiscountDealSet() {
        return this.discountDealSet;
    }


}

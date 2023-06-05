package com.bullish.test.model;
import java.util.List;

public abstract class DiscountDeal {

    public String productName;
    public abstract void discountProduct(List<Product> basket);

}

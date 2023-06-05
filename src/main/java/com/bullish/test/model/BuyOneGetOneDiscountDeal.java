package com.bullish.test.model;

import java.util.List;

public class BuyOneGetOneDiscountDeal extends DiscountDeal {
    @Override
    public void discountProduct(List<Product> basket) {
        int count = 0;
        for (Product p: basket) {
            if (p.getName().equals(this.productName)) {
                count++;
                if (count % 2 == 0) {
                    p.setDiscountedPrice(p.getDiscountedPrice() * 0.5);
                }
            }
        }
    }
}

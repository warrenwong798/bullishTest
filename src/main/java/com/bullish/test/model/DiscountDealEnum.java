package com.bullish.test.model;

public enum DiscountDealEnum {
    BuyOneGetOneDiscountDeal(com.bullish.test.model.BuyOneGetOneDiscountDeal.class);

    private Class<?> discountClass;
    DiscountDealEnum(Class<?> discountClass) {
        this.discountClass = discountClass;
    }

    public Class<?> getDiscountClass() {
        return discountClass;
    }


}

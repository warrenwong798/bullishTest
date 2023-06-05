package com.bullish.test.manager;

import com.bullish.test.model.DiscountDeal;
import com.bullish.test.model.Product;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ProductManager {

    private Map<String, Product> productMap;

    public ProductManager() {
        this.productMap = new ConcurrentHashMap<>();
    }

    public void addNewProduct(Product product) {
        productMap.put(product.getId(), product);
    }

    public void removeProduct(String id) {
        productMap.remove(id);
    }

    public Product getProduct(String id) {
        return productMap.getOrDefault(id, null);
    }

    public void addDiscountToProduct(String productId, DiscountDeal discountDeal) {
        if (productMap.containsKey(productId)) {
            discountDeal.productName = productMap.get(productId).getName();
            productMap.get(productId).addDiscount(discountDeal);
        }
    }

}

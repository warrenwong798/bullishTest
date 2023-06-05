package com.bullish.test.manager;

import com.bullish.test.model.BasketItem;
import com.bullish.test.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BasketManager {
    private Map<String, List<Product>> basketItemMap;

    public BasketManager() {
        this.basketItemMap = new ConcurrentHashMap<>();
    }

    public void addBasketItem(String customerId, Product product) {
        if (basketItemMap.containsKey(customerId)) {
            basketItemMap.get(customerId).add(product);
        }
        else {
            basketItemMap.put(customerId, new ArrayList<>());
            basketItemMap.get(customerId).add(product);
        }
    }

    public void removeBasketItem(String customerId, Product product) {
        if (basketItemMap.containsKey(customerId)) {
            basketItemMap.get(customerId).remove(product);
        }
    }

    public List<Product> getBasket(String customerId) {
        return basketItemMap.getOrDefault(customerId, null);
    }
}

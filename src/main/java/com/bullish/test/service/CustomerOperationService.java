package com.bullish.test.service;

import com.bullish.test.manager.BasketManager;
import com.bullish.test.manager.ProductManager;
import com.bullish.test.model.DiscountDeal;
import com.bullish.test.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerOperationService {

    @Autowired
    private ProductManager productManager;
    @Autowired
    private BasketManager basketManager;

    public void addToBasket(String customerId, String productId) {
        Product product = productManager.getProduct(productId);
        if (product != null) {
            basketManager.addBasketItem(customerId, product);
        }
    }

    public void removeFromBasket(String customerId, String productId) {
        Product product = productManager.getProduct(productId);
        if (product != null) {
            basketManager.removeBasketItem(customerId, product);
        }
    }

    public double check(String customerId) {
        List<Product> basket = basketManager.getBasket(customerId);
        if (basket != null) {
            basket = new ArrayList<>(basket);
            Set<DiscountDeal> discountDealSet = new HashSet<>();
            for (Product p: basket) {
                discountDealSet.addAll(p.getDiscountDealSet());
            }
            for (DiscountDeal discountDeal: discountDealSet) {
                discountDeal.discountProduct(basket);
            }
            double total = 0;
            for (Product p: basket) {
                total += p.getDiscountedPrice();
            }
            return total;
        }
        return 0;
    }
}

package com.bullish.test.service;

import com.bullish.test.dto.ProductDto;
import com.bullish.test.manager.ProductManager;
import com.bullish.test.model.DiscountDeal;
import com.bullish.test.model.DiscountDealEnum;
import com.bullish.test.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserOperationService {

    Logger logger = LogManager.getLogger(AdminUserOperationService.class);

    @Autowired
    private ProductManager productManager;

    public Product addProduct(ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getPrice());
        productManager.addNewProduct(product);
        return product;
    }

    public void removeProduct(String id) {
        productManager.removeProduct(id);
    }

    public void addDiscountDeal(String productId, String discountType) {
        try {
            Class<?> discountClass = DiscountDealEnum.valueOf(discountType).getDiscountClass();
            DiscountDeal discountDeal = (DiscountDeal) discountClass.getConstructor().newInstance();
            productManager.addDiscountToProduct(productId, discountDeal);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}

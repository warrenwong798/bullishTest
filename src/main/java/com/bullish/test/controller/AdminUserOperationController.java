package com.bullish.test.controller;

import com.bullish.test.dto.DiscountDealDto;
import com.bullish.test.dto.ProductDto;
import com.bullish.test.model.Product;
import com.bullish.test.service.AdminUserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminUserOperationController {

    @Autowired
    private AdminUserOperationService adminUserOperationService;

    @PostMapping("/addproduct")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto){
        Product product = adminUserOperationService.addProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/removeproduct")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void removeProduct(@RequestBody ProductDto productDto) {
        adminUserOperationService.removeProduct(productDto.getId());
    }

    @PostMapping("/adddiscountdeal")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void addDiscountDeal(@RequestBody DiscountDealDto discountDealDto) {
        adminUserOperationService.addDiscountDeal(discountDealDto.getProductId(), discountDealDto.getDiscountType());
    }
}

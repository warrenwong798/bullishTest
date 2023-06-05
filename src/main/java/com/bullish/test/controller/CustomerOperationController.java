package com.bullish.test.controller;

import com.bullish.test.dto.CustomerBasketOperationDto;
import com.bullish.test.model.Product;
import com.bullish.test.service.CustomerOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerOperationController {

    @Autowired
    private CustomerOperationService customerOperationService;

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void addToBasket(@RequestBody CustomerBasketOperationDto customerBasketOperationDto) {
        customerOperationService.addToBasket(customerBasketOperationDto.getCustomerId(), customerBasketOperationDto.getProductId());
    }

    @PostMapping("/remove")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void removeFromBasket(@RequestBody CustomerBasketOperationDto customerBasketOperationDto) {
        customerOperationService.removeFromBasket(customerBasketOperationDto.getCustomerId(), customerBasketOperationDto.getProductId());
    }

    @GetMapping("/check")
    public ResponseEntity<Double> check(@RequestParam String customerId) {
        return new ResponseEntity<Double>(customerOperationService.check(customerId), HttpStatus.OK);
    }
}

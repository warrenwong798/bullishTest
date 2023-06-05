package com.bullish.test;

import com.bullish.test.controller.AdminUserOperationController;
import com.bullish.test.controller.CustomerOperationController;
import com.bullish.test.dto.CustomerBasketOperationDto;
import com.bullish.test.dto.DiscountDealDto;
import com.bullish.test.dto.ProductDto;
import com.bullish.test.model.Product;
import com.bullish.test.service.AdminUserOperationService;
import com.bullish.test.service.CustomerOperationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {CustomerOperationController.class, AdminUserOperationController.class})
public class CustomerOperationTest {

    Logger logger = LogManager.getLogger(CustomerOperationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerOperationService customerOperationService;
    @MockBean
    private AdminUserOperationService adminUserOperationService;

    private String productId1;
    private String productId2;

    private void prepareProductTestData() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Fish");
        productDto.setPrice(103.1);
        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Meat");
        productDto2.setPrice(166.4);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        String json2 = "";
        try {
            json = ow.writeValueAsString((Object) productDto);
            json2 = ow.writeValueAsString((Object) productDto2);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/addproduct")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/admin/addproduct")
                .accept(MediaType.APPLICATION_JSON)
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON);

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();

            MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
            MockHttpServletResponse response2 = result2.getResponse();

            String jsonString = response.getContentAsString();
            String jsonString2 = response2.getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(jsonString, Product.class);
            Product product2 = objectMapper.readValue(jsonString2, Product.class);
            this.productId1 = product.getId();
            this.productId2 = product2.getId();

            DiscountDealDto discountDealDto = new DiscountDealDto();
            discountDealDto.setProductId(product.getId());
            discountDealDto.setDiscountType("BuyOneGetOneDiscountDeal");

            String addDiscountJson = "";
            try {
                addDiscountJson = ow.writeValueAsString((Object) discountDealDto);
                logger.info(addDiscountJson);
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }

            RequestBuilder addDiscountRequestBuilder = MockMvcRequestBuilders
                    .post("/admin/adddiscountdeal")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(addDiscountJson)
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult addDiscountResult = mockMvc.perform(addDiscountRequestBuilder).andReturn();
            MockHttpServletResponse addDiscountResponse = addDiscountResult.getResponse();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    public void addProductToBasketTest() {
        prepareProductTestData();
        CustomerBasketOperationDto customerBasketOperationDto = new CustomerBasketOperationDto();
        customerBasketOperationDto.setCustomerId("1");
        customerBasketOperationDto.setProductId(this.productId1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString((Object) customerBasketOperationDto);
            logger.info(json);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    public void removeProductToBasketTest() {
        prepareProductTestData();
        CustomerBasketOperationDto customerBasketOperationDto = new CustomerBasketOperationDto();
        customerBasketOperationDto.setCustomerId("1");
        customerBasketOperationDto.setProductId(this.productId1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString((Object) customerBasketOperationDto);
            logger.info(json);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();

            RequestBuilder removeRequestBuilder = MockMvcRequestBuilders
                    .post("/customer/remove")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult removeResult = mockMvc.perform(removeRequestBuilder).andReturn();
            MockHttpServletResponse removeResponse = removeResult.getResponse();

            assertEquals(HttpStatus.OK.value(), removeResponse.getStatus());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    public void checkTest() {
        prepareProductTestData();
        CustomerBasketOperationDto customerBasketOperationDto = new CustomerBasketOperationDto();
        customerBasketOperationDto.setCustomerId("1");
        customerBasketOperationDto.setProductId(this.productId1);
        CustomerBasketOperationDto customerBasketOperationDto2 = new CustomerBasketOperationDto();
        customerBasketOperationDto2.setCustomerId("1");
        customerBasketOperationDto2.setProductId(this.productId2);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString((Object) customerBasketOperationDto);
            logger.info(json);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            MvcResult result2 = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response2 = result2.getResponse();
            MvcResult result3 = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response3 = result3.getResponse();

            RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                    .post("/customer/add")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult result4 = mockMvc.perform(requestBuilder2).andReturn();
            MockHttpServletResponse response4 = result4.getResponse();
            MvcResult result5 = mockMvc.perform(requestBuilder2).andReturn();
            MockHttpServletResponse response5 = result5.getResponse();

            RequestBuilder checkRequestBuilder = MockMvcRequestBuilders
                    .get("/customer/check?customerId=1");

            MvcResult checkResult = mockMvc.perform(checkRequestBuilder).andReturn();
            MockHttpServletResponse checkResponse = checkResult.getResponse();


            assertEquals(HttpStatus.OK.value(), checkResponse.getStatus());
            logger.info(checkResponse.getContentAsString());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

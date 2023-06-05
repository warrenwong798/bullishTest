package com.bullish.test;

import com.bullish.test.controller.AdminUserOperationController;
import com.bullish.test.dto.DiscountDealDto;
import com.bullish.test.dto.ProductDto;
import com.bullish.test.manager.ProductManager;
import com.bullish.test.model.Product;
import com.bullish.test.service.AdminUserOperationService;
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
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AdminUserOperationController.class)
public class AdminUserOperationTest {

    Logger logger = LogManager.getLogger(AdminUserOperationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminUserOperationService adminUserOperationService;
    @MockBean
    private ProductManager productManager;

    @Test
    public void createNewProductTest() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Fish");
        productDto.setPrice(103.1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString((Object) productDto);
            logger.info(json);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/addproduct")
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
    public void removeProductTest() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Fish");
        productDto.setPrice(103.1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString((Object) productDto);
            logger.info(json);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/addproduct")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
//            Thread.sleep(1000);
            String jsonString = response.getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(jsonString, Product.class);

            logger.info("Product Id: " + product.getId());
            productDto.setId(product.getId());
            String json2 = ow.writeValueAsString((Object) productDto);

            RequestBuilder removeRequestBuilder = MockMvcRequestBuilders
                    .post("/admin/removeproduct")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json2)
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
    public void addDiscountDealTest() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Fish");
        productDto.setPrice(103.1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString((Object) productDto);
            logger.info(json);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/addproduct")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            String jsonString = response.getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(jsonString, Product.class);

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

            assertEquals(HttpStatus.OK.value(), addDiscountResponse.getStatus());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}

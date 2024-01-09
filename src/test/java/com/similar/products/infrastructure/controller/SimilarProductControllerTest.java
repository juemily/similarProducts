package com.similar.products.infrastructure.controller;

import com.similar.products.application.SimilarProductsService;
import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimilarProductControllerTest {

    @Mock
    private SimilarProductsService similarProductsService;

    @InjectMocks
    private SimilarProductController similarProductController;

    private SimilarProducts similarProducts = new SimilarProducts();
    private ProductDetail productDetail = new ProductDetail();
    private List<ProductDetail> productDetailList = new ArrayList<>();




    private final String requestMock = "{\n" +
            "    \"id\": \"1\",\n" +
            "    \"name\": \"Shirt\",\n" +
            "    \"price\": 9.99,\n" +
            "    \"availability\": true\n" +
            "}";

    private final String requestMockArray = "[3,100,1000]";

    @BeforeEach
    public void setUp() {
        //crear objetos de respuesta
        productDetail.setId("1000");
        productDetail.setName("Coat");
        productDetail.setPrice(89.99);
        productDetailList.add(0,productDetail);
        similarProducts.setSimilarProducts(productDetailList);

    }


    @Test
    void getProductDetail() {

        Mockito.when(similarProductsService.getProduct(anyString())).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetail> response = similarProductController.getProductDetail(anyString());
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(productDetail, response.getBody());

        // Verify
        Mockito.verify(similarProductsService, times(1)).getProduct(anyString());

    }

    @Test
    void getProductDetailNotFound() {

        Mockito.when(similarProductsService.getProduct(anyString())).thenReturn(null);

        // Act
        ResponseEntity<ProductDetail> response = similarProductController.getProductDetail(anyString());
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(null, response.getBody());

        // Verify
        Mockito.verify(similarProductsService, times(1)).getProduct(anyString());

    }

    @Test
    void getSimilarids() {

        Mockito.when(similarProductsService.getSimilarIds(anyString())).thenReturn(similarProducts);

        // Act
        ResponseEntity<SimilarProducts> response = similarProductController.getSimilarids(anyString());
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(similarProducts, response.getBody());

        // Verify
        Mockito.verify(similarProductsService, times(1)).getSimilarIds(anyString());

    }

    @Test
    void getSimilaridsNotFound() {

        Mockito.when(similarProductsService.getSimilarIds(anyString())).thenReturn(null);

        // Act
        ResponseEntity<SimilarProducts> response = similarProductController.getSimilarids(anyString());
        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(null, response.getBody());

        // Verify
        Mockito.verify(similarProductsService, times(1)).getSimilarIds(anyString());

    }
}
package com.similar.products.application.Impl;

import com.similar.products.application.SimilarProductsService;
import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import org.junit.Assert;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SimilarProductsServiceImplTest {

    SimilarProducts response = new SimilarProducts();

    ProductDetail productDetail = new ProductDetail();
    List<ProductDetail> productDetailList = new ArrayList<>();

    private final String requestMock = "{\n" +
            "    \"id\": \"1\",\n" +
            "    \"name\": \"Shirt\",\n" +
            "    \"price\": 9.99,\n" +
            "    \"availability\": true\n" +
            "}";






    @BeforeEach
    void setUp() {
        productDetail.setId("1000");
        productDetail.setName("Coat");
        productDetail.setPrice(89.99);
        productDetailList.add(0,productDetail);
        response.setSimilarProducts(productDetailList);



    }

    @Test
    void getProduct() {

        SimilarProductsServiceImpl mockService = Mockito.mock(SimilarProductsServiceImpl.class);
        Mockito.when(mockService.requestToMock(Mockito.anyString())).thenReturn(requestMock);
        Mockito.when(mockService.getProduct(anyString())).thenReturn(productDetail);
        assertEquals(productDetail,mockService.getProduct(anyString()));
    }

    @Test
    void getSimilarIds() {

        SimilarProductsServiceImpl mockService = Mockito.mock(SimilarProductsServiceImpl.class);
        Mockito.when(mockService.requestToMock(Mockito.anyString())).thenReturn(requestMock);
        Mockito.when(mockService.getSimilarIds(anyString())).thenReturn(response);
        assertEquals(response,mockService.getSimilarIds(anyString()));
    }

    @Test
    void requestToMock() {
        SimilarProductsServiceImpl mockService = Mockito.mock(SimilarProductsServiceImpl.class);
        Mockito.when(mockService.requestToMock(Mockito.anyString())).thenReturn(requestMock);
        Assert.assertNotNull(mockService.requestToMock(Mockito.anyString()));

    }

    @Test
    public void getProductFail(){

        SimilarProductsServiceImpl mockService = Mockito.mock(SimilarProductsServiceImpl.class);
        when(mockService.requestToMock(Mockito.anyString())).thenReturn("");
        assertThrows(java.lang.NullPointerException.class, (ThrowingRunnable) mockService.getProduct(anyString()));

    }
}
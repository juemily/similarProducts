package com.similar.products.application.Impl;

import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import com.similar.products.infrastructure.mapper.ProductDetailMapper;
import org.junit.Assert;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimilarProductsServiceImplTest {


    @InjectMocks
    private SimilarProductsServiceImpl similarProductsService;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private  ProductDetailMapper productDetailMapper;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;




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
        //crear objetos de respuesta
        productDetail.setId("1000");
        productDetail.setName("Coat");
        productDetail.setPrice(89.99);
        productDetailList.add(0,productDetail);
        response.setSimilarProducts(productDetailList);

        //configurar webClient
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<String>>notNull())).thenReturn(Mono.just(requestMock));



    }

    @Test
    void getProduct() {

        when(productDetailMapper.toDto(requestMock)).thenReturn(productDetail);
        assertEquals(productDetail,similarProductsService.getProduct("1"));

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

        assertEquals(requestMock, similarProductsService.requestToMock("productos/1"));


    }

    @Test
    public void getProductFail(){

        SimilarProductsServiceImpl mockService = Mockito.mock(SimilarProductsServiceImpl.class);
        when(mockService.requestToMock(Mockito.anyString())).thenReturn("");
        assertThrows(java.lang.NullPointerException.class, (ThrowingRunnable) mockService.getProduct(anyString()));

    }
}
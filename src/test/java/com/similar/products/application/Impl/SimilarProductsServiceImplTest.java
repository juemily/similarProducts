package com.similar.products.application.Impl;

import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import com.similar.products.infrastructure.mapper.ProductDetailMapper;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimilarProductsServiceImplTest {


    @InjectMocks
    private SimilarProductsServiceImpl similarProductsService;

    @Mock
    private WebClient webClientMock;

    @Mock
    private SimilarProductsServiceImpl mockServiceResponse;

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

    private SimilarProducts response = new SimilarProducts();
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

    }

    @Test
    void getProduct() {
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<String>>notNull())).thenReturn(Mono.just(requestMock));

        when(productDetailMapper.toDto(requestMock)).thenReturn(productDetail);
        assertEquals(productDetail,similarProductsService.getProduct("1"));

    }

    @Test
    void getSimilarIds() {

        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<String>>notNull())).thenReturn(Mono.just(requestMockArray));
        when(mockServiceResponse.requestToMock("product/3")).thenReturn(requestMock);
        when(mockServiceResponse.requestToMock("product/100")).thenReturn(requestMock);
        when(mockServiceResponse.requestToMock("product/1000")).thenReturn(requestMock);
        when(productDetailMapper.toDto(requestMock)).thenReturn(productDetail);
        when(mockServiceResponse.getProduct(anyString())).thenReturn(productDetail);
        assertNotNull(similarProductsService.getSimilarIds("1"));

    }

    @Test
    void requestToMock() {
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<String>>notNull())).thenReturn(Mono.just(requestMock));

        assertEquals(requestMock, similarProductsService.requestToMock("productos/1"));


    }

}
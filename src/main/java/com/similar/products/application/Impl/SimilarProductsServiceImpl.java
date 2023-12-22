package com.similar.products.application.Impl;

import com.similar.products.application.SimilarProductsService;
import com.similar.products.domain.exceptions.SimilarProductsException;
import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import com.similar.products.infrastructure.mapper.ProductDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimilarProductsServiceImpl implements SimilarProductsService {

    private final WebClient webClient;

    private final ProductDetailMapper productDetailMapper;


    @Override
    @Cacheable("getProduct")
    public ProductDetail getProduct(String productId) throws SimilarProductsException {
        log.info("*********** getProduct service ***********");
        String resource = requestToMock(String.format("product/%s", productId));

        if (!resource.isBlank()) {
            return productDetailMapper.toDto(resource);
        } else {
            throw new SimilarProductsException(HttpStatus.NOT_FOUND.value(), String.format("Not found id: %s", productId));
        }

    }

    @Override
    @Cacheable("getProduct")
    public SimilarProducts getSimilarIds(String productId) throws SimilarProductsException {
        log.info("*********** getSimilarIds service ***********");

        SimilarProducts similarProducts = new SimilarProducts();
        List<ProductDetail> productDetailList = new ArrayList<>();

        String resource = requestToMock(String.format("product/%s/similarids", productId));
        resource = resource.replace("[", "");
        resource = resource.replace("]", "");


        if (!resource.isBlank()) {
            List<Integer> cleanList = Arrays.asList(resource.split(",")).stream()
                    .map(Integer::parseInt).toList();

            for (Integer id : cleanList) {


                log.info("*********** id to search ***********" + id);
                String product = requestToMock(String.format("product/%s", id));
                if (product != null && !product.isBlank()) {
                    productDetailList.add(productDetailMapper.toDto(product));
                }

            }

        } else {
            throw new SimilarProductsException(HttpStatus.NOT_FOUND.value(), "got an empty list ");
        }

        similarProducts.setSimilarProducts(productDetailList);
        return similarProducts;

    }

    @SneakyThrows
    @Async
    public String requestToMock(String url) throws SimilarProductsException {
        log.info("*********** requestToMock service ***********");
        AtomicReference<String> response = new AtomicReference<>(new String());
        Mono<String> request = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .retry(2);
        request.subscribe(
                result -> {
                    response.setPlain(result);
                    log.info(result);
                },
                error -> {
                    log.error(error.getMessage());
                    throw new SimilarProductsException(error.getMessage(), error.getCause(), HttpStatus.NOT_FOUND.value());
                }
        );
        request.block();
        return response.toString();
    }
}

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
import reactor.core.publisher.Flux;
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
    /**
     * getProduct: construye url para realizar una peticion a un webclient de devolvera un string
     * @Params: productId: String
     * @Return: ProductDetail: objeto con detalles de producto
     * @Author: Jmaluenga
     * */
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

    /**
     * getSimilarIds: construye url para realizar una peticion a un webclient de devolvera un string con formato de array
        en caso que exista una primera respuesta se utiliza recursividad para armar una nueva url y optener los productos similares
        que se agregarán a una lista de produtos similares.

     * @Params: productId: String
     * @Return: SimilarProducts: lista de productos similares
     * @Author: Jmaluenga
     * */
    @Override
    @Cacheable("getSimilarIds")
    public SimilarProducts getSimilarIds(String productId) throws SimilarProductsException {
        log.info("*********** getSimilarIds service ***********");

        SimilarProducts similarProducts = new SimilarProducts();
        List<ProductDetail> productDetailList = new ArrayList<>();

        String resource = requestToMock(String.format("product/%s/similarids", productId));
        resource = resource.replaceAll("^\\[|\\]$", "");



        if (!resource.isBlank()) {
            List<Integer> cleanList = Arrays.asList(resource.split(",")).stream()
                    .map(Integer::parseInt).toList();

            productDetailList = Flux.fromIterable(cleanList)
                    .flatMap(id -> Mono.fromSupplier(() -> getProduct(id.toString())))
                    .filter(temporalDetail -> temporalDetail != null)
                    .collectList()
                    .block();


        } else {
            throw new SimilarProductsException(HttpStatus.NOT_FOUND.value(), "got an empty list ");
        }

        similarProducts.setSimilarProducts(productDetailList);
        return similarProducts;

    }

    /**
     * requestToMock: realiza una petición a un web client con la url que se le pase como string
     * dicha petición tendrá dos reintentos por cada  llamado a esta función
     * @Params: Url: String
     * @Return: respuesta del cliente web como string
     * @Author: Jmaluenga
     * */
    @SneakyThrows
    @Async
    public String requestToMock(String url) throws SimilarProductsException {
        log.info("*********** requestToMock service ***********");
        AtomicReference<String> response = new AtomicReference<>();
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

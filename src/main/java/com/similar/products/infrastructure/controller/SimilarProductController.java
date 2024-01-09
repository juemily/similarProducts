package com.similar.products.infrastructure.controller;

import com.similar.products.application.SimilarProductsService;
import com.similar.products.domain.exceptions.SimilarProductsException;
import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimilarProductController implements SimilarProductApi {

    private final SimilarProductsService similarProductsService;

    public SimilarProductController(SimilarProductsService similarProductsService) {
        this.similarProductsService = similarProductsService;
    }

    @Override
    public ResponseEntity<ProductDetail>getProductDetail( String productsId) throws SimilarProductsException {
        ProductDetail response = similarProductsService.getProduct(productsId);

        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

   @Override
    public ResponseEntity<SimilarProducts>getSimilarids(String productsId)throws SimilarProductsException {
       SimilarProducts response = similarProductsService.getSimilarIds(productsId);
        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

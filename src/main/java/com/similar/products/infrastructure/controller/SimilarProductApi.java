package com.similar.products.infrastructure.controller;

import com.similar.products.domain.exceptions.SimilarProductsException;
import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Validated
@RequestMapping("/product")
public interface SimilarProductApi {

    @GetMapping(value = "/{productsId}", produces = {"application/json;charset=utf8"})
    ResponseEntity<ProductDetail> getProductDetail(@PathVariable("productsId")  String productsId) throws SimilarProductsException;

    @GetMapping("/{productsId}/similar")
    ResponseEntity<SimilarProducts>getSimilarids(@PathVariable("productsId")  String productsId)throws SimilarProductsException;
}

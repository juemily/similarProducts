package com.similar.products.application;

import com.similar.products.domain.model.resource.ProductDetail;

import java.util.List;

public interface SimilarProductsService {
    ProductDetail getProduct(String productId);
    List<Integer> getSimilarIds (String productId);
}

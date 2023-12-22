package com.similar.products.application;

import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.domain.model.resource.SimilarProducts;

import java.util.List;

public interface SimilarProductsService {
    ProductDetail getProduct(String productId);
    SimilarProducts getSimilarIds (String productId);
}

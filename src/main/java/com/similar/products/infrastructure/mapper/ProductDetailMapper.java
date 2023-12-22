package com.similar.products.infrastructure.mapper;

import com.similar.products.domain.model.resource.ProductDetail;

public interface ProductDetailMapper {

    ProductDetail toDto(String resource);
}

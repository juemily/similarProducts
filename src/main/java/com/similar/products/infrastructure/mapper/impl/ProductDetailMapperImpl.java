package com.similar.products.infrastructure.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.similar.products.domain.model.resource.ProductDetail;
import com.similar.products.infrastructure.mapper.ProductDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductDetailMapperImpl implements ProductDetailMapper {
    @Override
    public ProductDetail toDto(String resource) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetail response = new ProductDetail();

        try {

            response = objectMapper.readValue(resource, ProductDetail.class);

        } catch (Exception e) {
           log.error(e.getMessage());
        }
        return response;
    }
}

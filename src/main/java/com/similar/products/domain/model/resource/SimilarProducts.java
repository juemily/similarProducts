package com.similar.products.domain.model.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class SimilarProducts {

    List<ProductDetail> similarProducts = new ArrayList<>();
}

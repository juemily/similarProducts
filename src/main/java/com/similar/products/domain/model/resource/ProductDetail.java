package com.similar.products.domain.model.resource;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
public class ProductDetail {
    private String id;

    private String name;
    private Number price;
    private boolean availability;
}

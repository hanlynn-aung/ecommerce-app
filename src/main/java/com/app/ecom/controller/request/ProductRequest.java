package com.app.ecom.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("stock_quantity")
    private Integer stockQuantity;

    @JsonProperty("category")
    private String category;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("active")
    private Boolean active;
}

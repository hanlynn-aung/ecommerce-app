package com.app.ecom.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("item_quantity")
    private Integer itemQuantity;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("address")
    private AddressResponse address;

}



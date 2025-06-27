package com.app.ecom.controller.response;


import com.app.ecom.common.constant.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;
}

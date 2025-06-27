package com.app.ecom.service;

import com.app.ecom.controller.response.OrderResponse;

import java.util.Optional;

public interface OrderService {

    Optional<OrderResponse> createOrder(Long userId);
}

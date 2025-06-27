package com.app.ecom.controller;

import com.app.ecom.common.constant.AppConstant;
import com.app.ecom.common.response.HttpResponse;
import com.app.ecom.controller.response.OrderResponse;
import com.app.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@RequestHeader("X-User-ID") Long userId) {

        Map<String, Object> response = new HashMap<>();

        Optional<OrderResponse> orderResponse = this.orderService.createOrder(userId);

        response.put("order_response", orderResponse);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, response);
    }
}

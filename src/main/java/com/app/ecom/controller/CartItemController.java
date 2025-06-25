package com.app.ecom.controller;

import com.app.ecom.common.constant.AppConstant;
import com.app.ecom.common.response.HttpResponse;
import com.app.ecom.controller.request.CartItemRequest;
import com.app.ecom.controller.response.CartItemResponse;
import com.app.ecom.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCartItems(@RequestHeader("X-User-ID") Long userId) {

        Map<String, Object> data = new HashMap<>();

        List<CartItemResponse> cartItemResponses = this.cartItemService.getCartItemByUser(userId);

        data.put("cartItemResponses", cartItemResponses);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addItem(@RequestHeader("X-User-ID") Long userId,
                                     @RequestBody CartItemRequest cartItemRequest) {

        Map<String, Object> data = new HashMap<>();

        this.cartItemService.addToCart(userId, cartItemRequest);

        data.put("response", "Item added successfully.");

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<?> removeFromCart(@RequestHeader("X-User-ID") Long userId,
                                            @PathVariable("product_id") Long productId) {

        Map<String, Object> data = new HashMap<>();

        this.cartItemService.removeFromCart(userId, productId);

        data.put("response", "Item deleted successfully.");

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }
}

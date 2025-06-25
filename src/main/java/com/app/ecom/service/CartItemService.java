package com.app.ecom.service;

import com.app.ecom.controller.request.CartItemRequest;
import com.app.ecom.controller.response.CartItemResponse;

import java.util.List;

public interface CartItemService {

    void addToCart(Long userId, CartItemRequest cartItemRequest);

    void removeFromCart(Long userId, Long productId);

    List<CartItemResponse> getCartItemByUser(Long userId);
}

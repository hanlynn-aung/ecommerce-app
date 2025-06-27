package com.app.ecom.serviceImpl;

import com.app.ecom.common.exception.BadRequestException;
import com.app.ecom.common.exception.ResourceNotFoundException;
import com.app.ecom.controller.request.CartItemRequest;
import com.app.ecom.controller.response.AddressResponse;
import com.app.ecom.controller.response.CartItemResponse;
import com.app.ecom.entity.CartItem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.CartItemService;
import com.app.ecom.util.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


    @Override
    public List<CartItemResponse> getCartItemByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<CartItem> cartItems = this.cartItemRepository.findByUser(user);

        return mapToCartItemResponse(cartItems);
    }

    @Override
    public void addToCart(Long userId, CartItemRequest cartItemRequest) {
        Optional<Product> productOpt = this.productRepository.findById(cartItemRequest.getProductId());

        if (productOpt.isEmpty()) {
            throw new BadRequestException("Invalid", "Product Not Found");
        }

        Product product = productOpt.get();
        if (product.getStockQuantity() < cartItemRequest.getQuantity()){
            throw new BadRequestException("Invalid", "Quantity Not Enough");
        }

        Optional<User> userOpt = this.userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new BadRequestException("Invalid", "User not found");
        }

        User user = userOpt.get();
        CartItem existingCartItem = this.cartItemRepository.findByUserAndProduct(user, product);
        int requestedQuantity = cartItemRequest.getQuantity();

        if (existingCartItem != null) {
            int currentQuantity = existingCartItem.getQuantity();
            int newQuantity = currentQuantity + requestedQuantity;

            int additionalQuantity = newQuantity - currentQuantity;
            if (product.getStockQuantity() < additionalQuantity) {
                throw new BadRequestException("Invalid", "Insufficient stock for the updated quantity");
            }

            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemRepository.save(existingCartItem);

            product.setStockQuantity(product.getStockQuantity() - additionalQuantity);
            productRepository.save(product);
        }
        else {
            CartItem cartItem = Builder.of(CartItem::new)
                    .add(CartItem::setUser, user)
                    .add(CartItem::setProduct, product)
                    .add(CartItem::setQuantity, cartItemRequest.getQuantity())
                    .add(CartItem::setPrice, product.getPrice())
                    .build();

            this.cartItemRepository.save(cartItem);

            product.setStockQuantity(product.getStockQuantity() - requestedQuantity);
            this.productRepository.save(product);
        }
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));

        CartItem cartItem = this.cartItemRepository.findByUserAndProduct(user, product);

        if (cartItem == null) {
            throw new ResourceNotFoundException("CartItem", "UserId/ProductId", userId + "/" + productId);
        }

        this.cartItemRepository.deleteByUserAndProduct(user, product);

        product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());

        this.productRepository.save(product);
    }

    private List<CartItemResponse> mapToCartItemResponse(List<CartItem> cartItems) {

        List<CartItemResponse> cartItemResponses = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            CartItemResponse cartItemResponse = Builder.of(CartItemResponse::new)
                    .add(CartItemResponse::setUsername, cartItem.getUser() != null ? cartItem.getUser().getUsername() : null)
                    .add(CartItemResponse::setPhoneNumber, cartItem.getUser() != null ? cartItem.getUser().getPhoneNumber() : null)
                    .add(CartItemResponse::setProductName, cartItem.getProduct() != null ? cartItem.getProduct().getName() : null)
                    .add(CartItemResponse::setItemQuantity, cartItem.getQuantity() != null ? cartItem.getQuantity() : null)
                    .add(CartItemResponse::setPrice, cartItem.getPrice() != null ? cartItem.getPrice() : null)
                    .build();

            AddressResponse addressResponse = Builder.of(AddressResponse::new)
                    .add(AddressResponse::setStreet, cartItem.getUser().getAddress().getStreet())
                    .add(AddressResponse::setCity, cartItem.getUser().getAddress().getCity())
                    .add(AddressResponse::setState, cartItem.getUser().getAddress().getState())
                    .add(AddressResponse::setZipCode, cartItem.getUser().getAddress().getZipCode())
                    .add(AddressResponse::setCountry, cartItem.getUser().getAddress().getCountry())
                    .build();

            cartItemResponse.setAddress(addressResponse);

            cartItemResponses.add(cartItemResponse);
        }

        return cartItemResponses;
    }

    @Override
    public void clearCartItems(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

            this.cartItemRepository.deleteByUser(user);
    }
}

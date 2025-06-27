package com.app.ecom.serviceImpl;

import com.app.ecom.common.constant.OrderStatus;
import com.app.ecom.common.exception.ResourceNotFoundException;
import com.app.ecom.controller.response.CartItemResponse;
import com.app.ecom.controller.response.OrderItemResponse;
import com.app.ecom.controller.response.OrderResponse;
import com.app.ecom.entity.Order;
import com.app.ecom.entity.OrderItem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import com.app.ecom.repository.OrderItemRepository;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.CartItemService;
import com.app.ecom.service.OrderService;
import com.app.ecom.util.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartItemService cartItemService;

    private final ProductRepository productRepository;

    @Override
    public Optional<OrderResponse> createOrder(Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<CartItemResponse> cartItems = this.cartItemService.getCartItemByUser(userId);

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getItemQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Builder.of(Order::new)
                .add(Order::setUser, user)
                .add(Order::setOrderStatus, OrderStatus.CONFIRMED)
                .add(Order::setTotalAmount, totalPrice)
                .build();

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    Product product = this.productRepository.findByName(item.getProductName())
                            .orElseThrow(() -> new ResourceNotFoundException("Product", "name", item.getProductName()));

                    return Builder.of(OrderItem::new)
                            .add(OrderItem::setProduct, product)
                            .add(OrderItem::setQuantity, item.getItemQuantity())
                            .add(OrderItem::setPrice, item.getPrice())
                            .add(OrderItem::setOrder, order)
                            .build();
                })
                .toList();

        order.setOrderItems(orderItems);

        this.orderRepository.save(order);

        this.cartItemService.clearCartItems(userId);

        return mapToOrderResponse(order, orderItems);
    }


    private Optional<OrderResponse> mapToOrderResponse(Order order, List<OrderItem> orderItems) {
        OrderResponse orderResponse = Builder.of(OrderResponse::new)
                .add(OrderResponse::setOrderId, order.getId())
                .add(OrderResponse::setOrderStatus, order.getOrderStatus())
                .add(OrderResponse::setTotalAmount, order.getTotalAmount())
                .add(OrderResponse::setOrderItems, order.getOrderItems().stream()
                        .map(item -> Builder.of(OrderItemResponse::new)
                                .add(OrderItemResponse::setOrderItemId, item.getId())
                                .add(OrderItemResponse::setProductId, item.getProduct().getId())
                                .add(OrderItemResponse::setQuantity, item.getQuantity())
                                .add(OrderItemResponse::setPrice, item.getPrice())
                                .add(OrderItemResponse::setSubtotal, item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                                .build())
                        .toList())
                .build();

        return Optional.of(orderResponse);
    }
}

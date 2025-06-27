package com.app.ecom.repository;

import com.app.ecom.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends BaseRepository<OrderItem, Long> {
}

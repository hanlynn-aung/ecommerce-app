package com.app.ecom.repository;

import com.app.ecom.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>{
}

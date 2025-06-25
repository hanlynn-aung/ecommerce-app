package com.app.ecom.repository;

import com.app.ecom.entity.CartItem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);
}

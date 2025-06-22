package com.app.ecom.repository;

import com.app.ecom.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsProductByName(String name);

    List<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE " +
            "p.active = true " +
            "AND p.stockQuantity > 0 " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);
}

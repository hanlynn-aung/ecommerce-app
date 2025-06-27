package com.app.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product extends AuditEntity {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String category;

    private String imageUrl;

    private Boolean active = true;
}

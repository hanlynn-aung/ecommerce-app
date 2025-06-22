package com.app.ecom.service;

import com.app.ecom.controller.request.ProductRequest;
import com.app.ecom.controller.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    boolean deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);
}

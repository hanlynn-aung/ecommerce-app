package com.app.ecom.controller;

import com.app.ecom.common.constant.AppConstant;
import com.app.ecom.common.response.HttpResponse;
import com.app.ecom.controller.request.ProductRequest;
import com.app.ecom.controller.response.ProductResponse;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProducts() {

        Map<String, Object> data = new HashMap<>();

        List<ProductResponse> products = this.productService.getAllProducts();

        data.put("products", products);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchProducts(@RequestParam("keyword") String keyword) {

        Map<String, Object> data = new HashMap<>();

        List<ProductResponse> products = this.productService.searchProducts(keyword);

        data.put("products", products);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {

        Map<String, Object> data = new HashMap<>();

        ProductResponse productResponse  = this.productService.createProduct(productRequest);

        data.put("product", productResponse);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable("id")Long id,
                                           @RequestBody ProductRequest productRequest) {

        Map<String, Object> data = new HashMap<>();

        ProductResponse productResponse  = this.productService.updateProduct(id, productRequest);

        data.put("product", productResponse);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id")Long id) {

        Map<String, Object> data = new HashMap<>();

        this.productService.deleteProduct(id);

        data.put("product", "Product deleted successfully.");

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }
}

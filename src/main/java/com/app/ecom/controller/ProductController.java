package com.app.ecom.controller;

import com.app.ecom.controller.request.ProductRequest;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(this.productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchProducts(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(this.productService.searchProducts(keyword),HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {

        return new ResponseEntity<>(this.productService.createProduct(productRequest),
                HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable("id")Long id,
                                           @RequestBody ProductRequest productRequest) {

        return new ResponseEntity<>(this.productService.updateProduct(id,productRequest),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id")Long id) {

        return new ResponseEntity<>(this.productService.deleteProduct(id),
                HttpStatus.NO_CONTENT);
    }
}

package com.app.ecom.serviceImpl;

import com.app.ecom.common.exception.BadRequestException;
import com.app.ecom.common.exception.ResourceNotFoundException;
import com.app.ecom.controller.request.ProductRequest;
import com.app.ecom.controller.response.ProductResponse;
import com.app.ecom.entity.Product;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.service.ProductService;
import com.app.ecom.util.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
      return this.productRepository.findByActiveTrue()
              .stream().map(this::maptoProductResponse)
              .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return this.productRepository.searchProducts(keyword)
                .stream()
                .map(this::maptoProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        if (this.productRepository.existsProductByName(productRequest.getName())){
            throw new BadRequestException("Invalid", "Product already exist!");
        }

        Product product = Builder.of(Product::new)
                .build();

         product = createOrUpdateProduct(product, productRequest);

        this.productRepository.save(product);

        return maptoProductResponse(product);

    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", id));

        if (this.productRepository.existsProductByName(productRequest.getName()) &&
                !existingProduct.getName().equals(productRequest.getName())) {
            throw new BadRequestException("Invalid", "Another product with the same name already exists!");
        }
        
        createOrUpdateProduct(existingProduct, productRequest);
        this.productRepository.save(existingProduct);
        return maptoProductResponse(existingProduct);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", id));

        if (!product.getActive()) {
            throw new BadRequestException("Invalid Operation", "Product is already deleted!");
        }

        product.setActive(false);
            this.productRepository.save(product);
            return true;
    }

    private static Product createOrUpdateProduct(Product product, ProductRequest request) {
        return Builder.of(() -> product)
                .add(Product::setName, request.getName())
                .add(Product::setDescription, request.getDescription())
                .add(Product::setPrice, request.getPrice())
                .add(Product::setStockQuantity, request.getStockQuantity())
                .add(Product::setCategory, request.getCategory())
                .add(Product::setImageUrl, request.getImageUrl())
                .add(Product::setActive, true)
                .build();
    }


    private ProductResponse maptoProductResponse(Product product) {

        return Builder.of(ProductResponse::new)
                .add(ProductResponse::setId, product.getId())
                .add(ProductResponse::setName, product.getName())
                .add(ProductResponse::setDescription, product.getDescription())
                .add(ProductResponse::setCategory, product.getCategory())
                .add(ProductResponse::setPrice, product.getPrice())
                .add(ProductResponse::setStockQuantity, product.getStockQuantity())
                .add(ProductResponse::setImageUrl, product.getImageUrl())
                .add(ProductResponse::setActive, product.getActive())
                .build();
    }
}

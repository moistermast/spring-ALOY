package com.sda.java3.ecommerce.services.product;

import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.utils.ProductListFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> getFeaturedProducts();
    List<Product> getRecentProducts();
    List<Product> getProducts(ProductListFilter filter);
    void createDummyProducts();
    Product getProductById(String id);
    List<Product> findAll();
    List<Product> getAllProducts();
    Optional<Product> getProductById(UUID id);
    Product saveProduct(SaveProductRequest request);
    Optional<Product> updateProduct(UUID id, SaveProductRequest request);
    boolean deleteProduct(UUID id);
    UUID save(SaveProductRequest request);
}

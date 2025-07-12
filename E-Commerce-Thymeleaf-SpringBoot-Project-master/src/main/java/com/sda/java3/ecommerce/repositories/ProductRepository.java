package com.sda.java3.ecommerce.repositories;

import com.sda.java3.ecommerce.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "SELECT p FROM Product p WHERE p.featured = true")
    List<Product> getFeaturedProducts();

    @Query(nativeQuery = true, value = "SELECT * FROM ec_product p ORDER BY p.created_at DESC LIMIT 8")
    List<Product> getRecentProducts();

    @Query(nativeQuery = true, value = "SELECT * FROM ec_product p WHERE (:categoryId IS NULL OR p.category_id = CAST(:categoryId AS char(36))) " +
            "AND ((:query IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(CAST(:query AS VARCHAR)), '%')) " +
            "OR (:query IS NULL OR LOWER(p.description) LIKE CONCAT('%', LOWER(CAST(:query AS VARCHAR)), '%'))) " +
            "ORDER BY p.created_at DESC")
    List<Product> getList(
            @Param("categoryId") String categoryId,
            @Param("query") String query
    );
}

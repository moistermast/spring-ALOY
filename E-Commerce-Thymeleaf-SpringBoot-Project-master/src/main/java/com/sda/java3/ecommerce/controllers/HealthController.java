package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> healthInfo = new HashMap<>();
        
        try {
            // Check database connectivity
            long categoryCount = categoryService.getCategories().size();
            long productCount = productService.findAll().size();
            
            healthInfo.put("status", "UP");
            healthInfo.put("database", "CONNECTED");
            healthInfo.put("categories_count", categoryCount);
            healthInfo.put("products_count", productCount);
            healthInfo.put("timestamp", System.currentTimeMillis());
            
        } catch (Exception e) {
            healthInfo.put("status", "DOWN");
            healthInfo.put("database", "DISCONNECTED");
            healthInfo.put("error", e.getMessage());
            healthInfo.put("timestamp", System.currentTimeMillis());
        }
        
        return ResponseEntity.ok(healthInfo);
    }
} 
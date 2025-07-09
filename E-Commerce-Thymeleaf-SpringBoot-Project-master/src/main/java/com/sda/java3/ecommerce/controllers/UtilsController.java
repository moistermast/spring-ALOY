package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.services.category.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/utils")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UtilsController {
    protected @NonNull ProductService propertyService;
    protected @NonNull CategoryService categoryService;

    @ResponseBody
    @RequestMapping(value = "/create-initial-data")
    public ResponseEntity<String> createInitialData() {
        propertyService.createDummyProducts();
        return ResponseEntity.ok("Data created successfully!");
    }

    @ResponseBody
    @RequestMapping(value = "/debug-database")
    public ResponseEntity<Map<String, Object>> debugDatabase() {
        Map<String, Object> debugInfo = new HashMap<>();
        
        try {
            long categoryCount = categoryService.getCategories().size();
            long productCount = propertyService.findAll().size();
            
            debugInfo.put("categories_count", categoryCount);
            debugInfo.put("products_count", productCount);
            debugInfo.put("categories", categoryService.getCategories());
            debugInfo.put("products", propertyService.findAll());
            debugInfo.put("status", "success");
            
        } catch (Exception e) {
            debugInfo.put("status", "error");
            debugInfo.put("error", e.getMessage());
            debugInfo.put("stack_trace", e.getStackTrace());
        }
        
        return ResponseEntity.ok(debugInfo);
    }
}

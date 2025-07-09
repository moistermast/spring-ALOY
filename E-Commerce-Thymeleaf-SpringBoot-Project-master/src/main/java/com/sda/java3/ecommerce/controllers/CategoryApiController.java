package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.Category;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.category.SaveCategoryRequest;
import com.sda.java3.ecommerce.services.product.SaveProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody SaveCategoryRequest request) {
        Category category = categoryService.saveCategory(request);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @Valid @RequestBody SaveCategoryRequest request) {
        return categoryService.updateCategory(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


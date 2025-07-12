package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.domains.Category;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.utils.Breadcrumb;
import com.sda.java3.ecommerce.utils.ProductListFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController extends BaseController {

    public ProductController(ProductService productService, CategoryService categoryService) {
        super(productService, categoryService);
    }

    @GetMapping("/products")
    public String getProducts(ModelMap modelMap, ProductListFilter filter) {
        initModelMap(modelMap);
        return filterProducts(filter, modelMap);
    }

    @PostMapping("/products")
    public String filterProducts(@ModelAttribute ProductListFilter filter, ModelMap model) {
        initModelMap(model);
        model.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").last(true).build()
        ));
        model.addAttribute("products", productService.getProducts(filter));
        model.addAttribute("filter", filter);
        model.addAttribute("categories", categoryService.getCategories());
        return "product-list";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(@PathVariable String id, ModelMap modelMap) {
        initModelMap(modelMap);
        Product product = productService.getProductById(id);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name(product != null ? product.getName() : "").last(true).url(String.format("/product-detail/%s", id)).build()

        ));
        modelMap.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/product-list-detail/{id}")
    public String productListDetail(@PathVariable String id, ModelMap modelMap) {
        initModelMap(modelMap);
        Product product = productService.getProductById(id);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Products").url("/products").build(),
                Breadcrumb.builder().name(product != null ? product.getName() : "").last(true).url(String.format("/product-detail/%s", id)).build()

        ));
        modelMap.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/products/category/{id}")
    public String filterByCategory(@PathVariable String id, ModelMap modelMap) {
        initModelMap(modelMap);
        ProductListFilter filter = new ProductListFilter();
        filter.setCategoryId(id);
        return filterProducts(filter, modelMap);
    }

    @GetMapping("/catalogue")
    public String catalogue(ModelMap modelMap) {
        initModelMap(modelMap);
        
        // Get all categories and find the ones we need
        List<Category> allCategories = categoryService.getCategories();
        System.out.println("DEBUG: Found " + allCategories.size() + " categories");
        allCategories.forEach(cat -> System.out.println("DEBUG: Category: " + cat.getName() + " (ID: " + cat.getId() + ")"));
        
        Category braceletsCategory = allCategories.stream()
                .filter(cat -> "BRACELETS".equalsIgnoreCase(cat.getName()))
                .findFirst().orElse(null);
        Category ringsCategory = allCategories.stream()
                .filter(cat -> "RINGS".equalsIgnoreCase(cat.getName()))
                .findFirst().orElse(null);
        Category chainsCategory = allCategories.stream()
                .filter(cat -> "CHAINS".equalsIgnoreCase(cat.getName()))
                .findFirst().orElse(null);
        
        System.out.println("DEBUG: Bracelets category: " + (braceletsCategory != null ? braceletsCategory.getName() : "NOT FOUND"));
        System.out.println("DEBUG: Rings category: " + (ringsCategory != null ? ringsCategory.getName() : "NOT FOUND"));
        System.out.println("DEBUG: Chains category: " + (chainsCategory != null ? chainsCategory.getName() : "NOT FOUND"));
        
        // Get products by category using the actual category IDs
        ProductListFilter braceletsFilter = new ProductListFilter();
        if (braceletsCategory != null) {
            braceletsFilter.setCategoryId(braceletsCategory.getId().toString());
        }
        var braceletsProducts = productService.getProducts(braceletsFilter);
        System.out.println("DEBUG: Found " + braceletsProducts.size() + " bracelets products");
        modelMap.addAttribute("bracelets", braceletsProducts);
        
        ProductListFilter ringsFilter = new ProductListFilter();
        if (ringsCategory != null) {
            ringsFilter.setCategoryId(ringsCategory.getId().toString());
        }
        var ringsProducts = productService.getProducts(ringsFilter);
        System.out.println("DEBUG: Found " + ringsProducts.size() + " rings products");
        modelMap.addAttribute("rings", ringsProducts);
        
        ProductListFilter chainsFilter = new ProductListFilter();
        if (chainsCategory != null) {
            chainsFilter.setCategoryId(chainsCategory.getId().toString());
        }
        var chainsProducts = productService.getProducts(chainsFilter);
        System.out.println("DEBUG: Found " + chainsProducts.size() + " chains products");
        modelMap.addAttribute("chains", chainsProducts);
        modelMap.addAttribute("filter", new ProductListFilter());
        return "catalogue";
    }
}

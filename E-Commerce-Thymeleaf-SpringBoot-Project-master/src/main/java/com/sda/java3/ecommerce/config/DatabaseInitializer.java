package com.sda.java3.ecommerce.config;

import com.sda.java3.ecommerce.domains.Category;
import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.repositories.CategoryRepository;
import com.sda.java3.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Bean
    @Order(1)
    public CommandLineRunner initializeDatabase() {
        return args -> {
            log.info("Starting database initialization...");
            
            try {
                // Check if products exist, if not create them (this will also create categories if needed)
                if (productRepository.count() == 0) {
                    log.info("Creating initial data...");
                    createInitialProducts();
                } else {
                    log.info("Data already exists, skipping initialization.");
                }

                // Log the current state
                log.info("Categories count: {}", categoryRepository.count());
                log.info("Products count: {}", productRepository.count());
                
                // List all categories for debugging
                categoryRepository.findAll().forEach(cat -> 
                    log.info("Category: {} (ID: {})", cat.getName(), cat.getId())
                );

                log.info("Database initialization completed successfully!");
                
            } catch (Exception e) {
                log.error("Error during database initialization: ", e);
                throw new RuntimeException("Failed to initialize database", e);
            }
        };
    }



    private void createInitialProducts() {
        // Get or create categories
        Category ringsCategory = categoryRepository.findByName("RINGS")
                .orElseGet(() -> {
                    Category category = Category.builder()
                            .name("RINGS")
                            .description("Elegant rings for every occasion")
                            .createdAt(LocalDateTime.now())
                            .build();
                    return categoryRepository.save(category);
                });
        
        Category chainsCategory = categoryRepository.findByName("CHAINS")
                .orElseGet(() -> {
                    Category category = Category.builder()
                            .name("CHAINS")
                            .description("Stylish chains and necklaces")
                            .createdAt(LocalDateTime.now())
                            .build();
                    return categoryRepository.save(category);
                });
        
        Category braceletsCategory = categoryRepository.findByName("BRACELETS")
                .orElseGet(() -> {
                    Category category = Category.builder()
                            .name("BRACELETS")
                            .description("Beautiful bracelets and wrist accessories")
                            .createdAt(LocalDateTime.now())
                            .build();
                    return categoryRepository.save(category);
                });

        // Create sample products for each category
        createRingProducts(ringsCategory);
        createChainProducts(chainsCategory);
        createBraceletProducts(braceletsCategory);

        log.info("Created {} products", productRepository.count());
    }

    private void createRingProducts(Category ringsCategory) {
        // Sample ring products
        Product ring1 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Classic Silver Ring")
                .description("Elegant silver ring with timeless design")
                .price(new BigDecimal("89.99"))
                .views(15)
                .image("Rings/1.png")
                .sale(false)
                .featured(true)
                .featureImage("Rings/1.png")
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring1);

        Product ring2 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Modern Design Ring")
                .description("Contemporary ring with unique geometric patterns")
                .price(new BigDecimal("125.00"))
                .views(8)
                .image("Rings/3.png")
                .sale(true)
                .salePrice(new BigDecimal("99.99"))
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring2);

        Product ring3 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Elegant Band Ring")
                .description("Simple yet sophisticated band ring")
                .price(new BigDecimal("75.50"))
                .views(22)
                .image("Rings/IMGP7466.png")
                .sale(false)
                .featured(true)
                .featureImage("Rings/IMGP7466.png")
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring3);
    }

    private void createChainProducts(Category chainsCategory) {
        // Sample chain products
        Product chain1 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("Silver Cuban Chain")
                .description("Classic Cuban link chain in sterling silver")
                .price(new BigDecimal("120.00"))
                .views(25)
                .image("Chains/Silver Cuban.jpg")
                .sale(false)
                .featured(true)
                .featureImage("Chains/Silver Cuban.jpg")
                .size("18\",20\",22\",24\"")
                .color("Silver")
                .build();
        productRepository.save(chain1);

        Product chain2 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("Cuban Chain Front View")
                .description("Premium Cuban chain with detailed front view")
                .price(new BigDecimal("135.00"))
                .views(18)
                .image("Chains/Silver Cuban front.jpg")
                .sale(true)
                .salePrice(new BigDecimal("110.00"))
                .featured(false)
                .size("18\",20\",22\",24\"")
                .color("Silver")
                .build();
        productRepository.save(chain2);

        Product chain3 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("Lily Flower Chain")
                .description("Delicate chain with lily flower design")
                .price(new BigDecimal("85.00"))
                .views(20)
                .image("Chains/lily flower chain.jpg")
                .sale(false)
                .featured(false)
                .size("16\",18\",20\"")
                .color("Silver")
                .build();
        productRepository.save(chain3);
    }

    private void createBraceletProducts(Category braceletsCategory) {
        // Sample bracelet products
        Product bracelet1 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Couple Black Bracelet")
                .description("Matching black bracelets for couples")
                .price(new BigDecimal("65.00"))
                .views(30)
                .image("Bracelets/couple black.jpg")
                .sale(false)
                .featured(true)
                .featureImage("Bracelets/couple black.jpg")
                .size("7\",8\",9\"")
                .color("Black")
                .build();
        productRepository.save(bracelet1);

        Product bracelet2 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Couple White Bracelet")
                .description("Elegant white bracelets for couples")
                .price(new BigDecimal("70.00"))
                .views(28)
                .image("Bracelets/couple white.jpg")
                .sale(true)
                .salePrice(new BigDecimal("55.00"))
                .featured(false)
                .size("7\",8\",9\"")
                .color("White")
                .build();
        productRepository.save(bracelet2);

        Product bracelet3 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Yin Yang Couple Bracelet")
                .description("Yin yang design couple bracelets")
                .price(new BigDecimal("85.00"))
                .views(35)
                .image("Bracelets/Couple yin yan-min.png")
                .sale(false)
                .featured(true)
                .featureImage("Bracelets/Couple yin yan-min.png")
                .size("7\",8\",9\"")
                .color("Black/White")
                .build();
        productRepository.save(bracelet3);
    }
} 
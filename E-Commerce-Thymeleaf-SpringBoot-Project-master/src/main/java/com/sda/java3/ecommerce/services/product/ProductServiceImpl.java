package com.sda.java3.ecommerce.services.product;

import com.sda.java3.ecommerce.domains.Category;
import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.repositories.CategoryRepository;
import com.sda.java3.ecommerce.repositories.ProductRepository;
import com.sda.java3.ecommerce.utils.ProductListFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(final ProductRepository productRepository, final CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(SaveProductRequest request) {
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(UUID.fromString(request.getCategoryId())).orElse(null);
        }
        
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(new BigDecimal(request.getPrice()))
                .image(request.getImageUrl())
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();
        
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(UUID id, SaveProductRequest request) {
        return productRepository.findById(id).map(product -> {
            Category category = null;
            if (request.getCategoryId() != null) {
                category = categoryRepository.findById(UUID.fromString(request.getCategoryId())).orElse(null);
            }
            
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(new BigDecimal(request.getPrice()));
            product.setImage(request.getImageUrl());
            product.setCategory(category);
            
            return productRepository.save(product);
        });
    }

    @Override
    public boolean deleteProduct(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getFeaturedProducts() {
        return productRepository.getFeaturedProducts();
    }

    @Override
    public List<Product> getRecentProducts() {
        return productRepository.getRecentProducts();
    }

    @Override
    public List<Product> getProducts(ProductListFilter filter) {
        Category category = null;
        if (filter != null && filter.getCategoryId() != null && !filter.getCategoryId().isEmpty()) {
            try {
                category = categoryRepository.getById(UUID.fromString(filter.getCategoryId()));
            if (category != null)
                filter.setCategoryName(category.getName());
            } catch (IllegalArgumentException e) {
                // Invalid UUID format, ignore
            }
        }

        // Debug logging for query parameter
        System.out.println("DEBUG: filter.getQuery() type = " + (filter != null && filter.getQuery() != null ? filter.getQuery().getClass().getSimpleName() : "null"));
        System.out.println("DEBUG: filter.getQuery() value = " + (filter != null ? filter.getQuery() : "null"));

        String queryParam = (filter != null && filter.getQuery() != null) ? filter.getQuery() : null;
        String categoryIdParam = (category != null) ? category.getId().toString() : null;

        return productRepository.getList(categoryIdParam, queryParam);
    }

    @Override
    public Product getProductById(String id) {
        try {
            Product product = productRepository.getById(UUID.fromString(id));
            product.setViews(product.getViews() + 1);
            productRepository.save(product);
            return product;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public UUID save(SaveProductRequest request) {
        Product product = saveProduct(request);
        return product.getId();
    }

    @Override
    public void createDummyProducts() {
        // Create categories
        Category ringsCategory = Category.builder()
                .name("RINGS")
                .description("Elegant rings for every occasion")
                .createdAt(LocalDateTime.now())
                .build();
        categoryRepository.save(ringsCategory);

        Category chainsCategory = Category.builder()
                .name("CHAINS")
                .description("Stylish chains and necklaces")
                .createdAt(LocalDateTime.now())
                .build();
        categoryRepository.save(chainsCategory);

        Category braceletsCategory = Category.builder()
                .name("BRACELETS")
                .description("Beautiful bracelets and wrist accessories")
                .createdAt(LocalDateTime.now())
                .build();
        categoryRepository.save(braceletsCategory);

        // Rings Products
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

        Product ring4 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Statement Ring")
                .description("Bold statement ring for special occasions")
                .price(new BigDecimal("150.00"))
                .views(12)
                .image("Rings/IMGP7471.png")
                .sale(false)
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring4);

        Product ring5 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Minimalist Ring")
                .description("Clean and minimal ring design")
                .price(new BigDecimal("65.00"))
                .views(18)
                .image("Rings/IMGP7474.png")
                .sale(true)
                .salePrice(new BigDecimal("49.99"))
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring5);

        Product ring6 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Artistic Ring")
                .description("Handcrafted artistic ring with unique details")
                .price(new BigDecimal("180.00"))
                .views(9)
                .image("Rings/IMGP7475.png")
                .sale(false)
                .featured(true)
                .featureImage("Rings/IMGP7475.png")
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring6);

        Product ring7 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Textured Ring")
                .description("Ring with beautiful textured surface")
                .price(new BigDecimal("95.00"))
                .views(14)
                .image("Rings/IMGP7476.png")
                .sale(false)
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring7);

        Product ring8 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Premium Ring")
                .description("High-quality premium ring with exceptional finish")
                .price(new BigDecimal("220.00"))
                .views(6)
                .image("Rings/IMGP7482.jpg")
                .sale(false)
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring8);

        Product ring9 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Designer Ring")
                .description("Exclusive designer ring with intricate details")
                .price(new BigDecimal("280.00"))
                .views(11)
                .image("Rings/IMGP7487.png")
                .sale(true)
                .salePrice(new BigDecimal("225.00"))
                .featured(true)
                .featureImage("Rings/IMGP7487.png")
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring9);

        Product ring10 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Luxury Ring")
                .description("Luxurious ring with premium materials")
                .price(new BigDecimal("350.00"))
                .views(7)
                .image("Rings/IMGP7491.jpg")
                .sale(false)
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring10);

        Product ring11 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Signature Ring")
                .description("Signature collection ring with unique character")
                .price(new BigDecimal("165.00"))
                .views(13)
                .image("Rings/IMGP7492.png")
                .sale(false)
                .featured(false)
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring11);

        Product ring12 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(ringsCategory)
                .name("Collection Ring")
                .description("Part of our exclusive collection series")
                .price(new BigDecimal("195.00"))
                .views(10)
                .image("Rings/IMGP7493.png")
                .sale(false)
                .featured(true)
                .featureImage("Rings/IMGP7493.png")
                .size("6,7,8,9,10")
                .color("Silver")
                .build();
        productRepository.save(ring12);

        // Chains Products
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

        Product chain4 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("One Chain Design")
                .description("Unique single-link chain design")
                .price(new BigDecimal("95.00"))
                .views(15)
                .image("Chains/one chain.jpg")
                .sale(false)
                .featured(true)
                .featureImage("Chains/one chain.jpg")
                .size("18\",20\",22\"")
                .color("Silver")
                .build();
        productRepository.save(chain4);

        Product chain5 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("Joker Chain")
                .description("Playful joker-themed chain design")
                .price(new BigDecimal("110.00"))
                .views(12)
                .image("Chains/Joker chain.jpg")
                .sale(true)
                .salePrice(new BigDecimal("89.99"))
                .featured(false)
                .size("18\",20\",22\"")
                .color("Silver")
                .build();
        productRepository.save(chain5);

        Product chain6 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("Spark Chain")
                .description("Chain with sparkle and shine effect")
                .price(new BigDecimal("75.00"))
                .views(22)
                .image("Chains/spark chain.jpg")
                .sale(false)
                .featured(false)
                .size("16\",18\",20\"")
                .color("Silver")
                .build();
        productRepository.save(chain6);

        Product chain7 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(chainsCategory)
                .name("Zeus Mark Rope Chain")
                .description("Rope chain with Zeus mark design")
                .price(new BigDecimal("145.00"))
                .views(16)
                .image("Chains/zeus mark (rope).jpg")
                .sale(false)
                .featured(true)
                .featureImage("Chains/zeus mark (rope).jpg")
                .size("18\",20\",22\",24\"")
                .color("Silver")
                .build();
        productRepository.save(chain7);

        // Bracelets Products
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

        Product bracelet4 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Marble White Bracelet")
                .description("Beautiful marble white bracelet")
                .price(new BigDecimal("55.00"))
                .views(25)
                .image("Bracelets/Marbel white.jpg")
                .sale(false)
                .featured(false)
                .size("7\",8\",9\"")
                .color("White")
                .build();
        productRepository.save(bracelet4);

        Product bracelet5 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Premium Bracelet")
                .description("High-quality premium bracelet")
                .price(new BigDecimal("120.00"))
                .views(18)
                .image("Bracelets/IMGP7521.jpg")
                .sale(false)
                .featured(true)
                .featureImage("Bracelets/IMGP7521.jpg")
                .size("7\",8\",9\"")
                .color("Silver")
                .build();
        productRepository.save(bracelet5);

        Product bracelet6 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Designer Bracelet")
                .description("Exclusive designer bracelet")
                .price(new BigDecimal("95.00"))
                .views(20)
                .image("Bracelets/WhatsApp Image 2025-04-30 at 22.43.50_2aeafd5d.jpg")
                .sale(true)
                .salePrice(new BigDecimal("75.00"))
                .featured(false)
                .size("7\",8\",9\"")
                .color("Silver")
                .build();
        productRepository.save(bracelet6);

        Product bracelet7 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Elegant Bracelet")
                .description("Elegant and sophisticated bracelet design")
                .price(new BigDecimal("80.00"))
                .views(22)
                .image("Bracelets/WhatsApp Image 2025-04-30 at 22.43.50_2b186157.jpg")
                .sale(false)
                .featured(false)
                .size("7\",8\",9\"")
                .color("Silver")
                .build();
        productRepository.save(bracelet7);

        Product bracelet8 = Product.builder()
                .createdAt(LocalDateTime.now())
                .category(braceletsCategory)
                .name("Modern Bracelet")
                .description("Contemporary modern bracelet design")
                .price(new BigDecimal("90.00"))
                .views(19)
                .image("Bracelets/WhatsApp Image 2025-04-30 at 22.43.50_36ac30f8.jpg")
                .sale(false)
                .featured(true)
                .featureImage("Bracelets/WhatsApp Image 2025-04-30 at 22.43.50_36ac30f8.jpg")
                .size("7\",8\",9\"")
                .color("Silver")
                .build();
        productRepository.save(bracelet8);
    }
}

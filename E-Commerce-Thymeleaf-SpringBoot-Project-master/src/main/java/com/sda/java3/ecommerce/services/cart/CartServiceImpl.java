package com.sda.java3.ecommerce.services.cart;

import com.sda.java3.ecommerce.domains.Cart;
import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.repositories.CartRepository;
import com.sda.java3.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {
    
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    
    // In-memory cart for users without accounts
    private final Map<String, List<CartItem>> sessionCarts = new ConcurrentHashMap<>();
    
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    public List<Cart> getCartItems(User user) {
        if (user != null) {
            return cartRepository.findByUser(user);
        }
        return new ArrayList<>();
    }
    
    @Override
    public List<CartItem> getSessionCartItems(String sessionId) {
        return sessionCarts.getOrDefault(sessionId, new ArrayList<>());
    }
    
    @Override
    public Cart addToCart(User user, Product product, Integer quantity) {
        if (user != null) {
            Cart existingCart = cartRepository.findByUserAndProduct(user, product);
            
            if (existingCart != null) {
                existingCart.setQuantity(existingCart.getQuantity() + quantity);
                return cartRepository.save(existingCart);
            } else {
                Cart cart = Cart.builder()
                        .user(user)
                        .product(product)
                        .quantity(quantity)
                        .createdAt(LocalDateTime.now())
                        .build();
                return cartRepository.save(cart);
            }
        }
        return null;
    }
    
    @Override
    public void addToSessionCart(String sessionId, UUID productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;
        
        List<CartItem> cartItems = sessionCarts.getOrDefault(sessionId, new ArrayList<>());
        
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
        
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setId(UUID.randomUUID());
            newItem.setProductId(productId);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCreatedAt(LocalDateTime.now());
            cartItems.add(newItem);
        }
        
        sessionCarts.put(sessionId, cartItems);
    }
    
    @Override
    public void removeFromCart(UUID cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
    
    @Override
    public void removeFromSessionCart(String sessionId, UUID cartItemId) {
        List<CartItem> cartItems = sessionCarts.get(sessionId);
        if (cartItems != null) {
            cartItems.removeIf(item -> item.getId().equals(cartItemId));
            sessionCarts.put(sessionId, cartItems);
        }
    }
    
    @Override
    public void updateQuantity(UUID cartItemId, Integer quantity) {
        cartRepository.findById(cartItemId).ifPresent(cart -> {
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        });
    }
    
    @Override
    public void updateSessionCartQuantity(String sessionId, UUID cartItemId, Integer quantity) {
        List<CartItem> cartItems = sessionCarts.get(sessionId);
        if (cartItems != null) {
            cartItems.stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
            sessionCarts.put(sessionId, cartItems);
        }
    }
    
    @Override
    public void clearCart(User user) {
        if (user != null) {
            List<Cart> cartItems = cartRepository.findByUser(user);
            cartRepository.deleteAll(cartItems);
        }
    }
    
    @Override
    public void clearSessionCart(String sessionId) {
        sessionCarts.remove(sessionId);
    }
    
    @Override
    public BigDecimal getSessionCartTotal(String sessionId) {
        List<CartItem> cartItems = getSessionCartItems(sessionId);
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    @Override
    public int getSessionCartItemCount(String sessionId) {
        List<CartItem> cartItems = getSessionCartItems(sessionId);
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }
    
    // Inner class for session-based cart items
    public static class CartItem {
        private UUID id;
        private UUID productId;
        private Product product;
        private Integer quantity;
        private LocalDateTime createdAt;
        
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public UUID getProductId() { return productId; }
        public void setProductId(UUID productId) { this.productId = productId; }
        
        public Product getProduct() { return product; }
        public void setProduct(Product product) { this.product = product; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}

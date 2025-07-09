package com.sda.java3.ecommerce.services.cart;

import com.sda.java3.ecommerce.domains.Cart;
import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.domains.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CartService {
    List<Cart> getCartItems(User user);
    List<CartServiceImpl.CartItem> getSessionCartItems(String sessionId);
    Cart addToCart(User user, Product product, Integer quantity);
    void addToSessionCart(String sessionId, UUID productId, Integer quantity);
    void removeFromCart(UUID cartItemId);
    void removeFromSessionCart(String sessionId, UUID cartItemId);
    void updateQuantity(UUID cartItemId, Integer quantity);
    void updateSessionCartQuantity(String sessionId, UUID cartItemId, Integer quantity);
    void clearCart(User user);
    void clearSessionCart(String sessionId);
    BigDecimal getSessionCartTotal(String sessionId);
    int getSessionCartItemCount(String sessionId);
}

package com.sda.java3.ecommerce.repositories;

import com.sda.java3.ecommerce.domains.Cart;
import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
}

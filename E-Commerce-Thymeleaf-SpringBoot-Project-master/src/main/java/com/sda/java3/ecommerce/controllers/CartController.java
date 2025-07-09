package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.services.cart.CartService;
import com.sda.java3.ecommerce.services.cart.CartServiceImpl;
import com.sda.java3.ecommerce.utils.Breadcrumb;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class CartController extends BaseController {

    private final CartService cartService;

    public CartController(ProductService productService, CategoryService categoryService, CartService cartService) {
        super(productService, categoryService);
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String viewCart(ModelMap modelMap, HttpSession session) {
        initModelMap(modelMap);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Cart").url("/cart").last(true).build()
        ));
        
        // Get session ID for cart management
        String sessionId = session.getId();
        List<CartServiceImpl.CartItem> cartItems = cartService.getSessionCartItems(sessionId);
        BigDecimal subtotal = cartService.getSessionCartTotal(sessionId);
        BigDecimal shipping = new BigDecimal("4.00");
        BigDecimal grandTotal = subtotal.add(shipping);
        
        modelMap.addAttribute("cartItems", cartItems);
        modelMap.addAttribute("subtotal", subtotal.toString());
        modelMap.addAttribute("shipping", shipping.toString());
        modelMap.addAttribute("grandTotal", grandTotal.toString());
        modelMap.addAttribute("itemCount", cartService.getSessionCartItemCount(sessionId));
        
        return "cart";
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public String addToCart(@RequestParam String productId, 
                           @RequestParam(defaultValue = "1") Integer quantity,
                           HttpSession session) {
        try {
            UUID productUUID = UUID.fromString(productId);
            cartService.addToSessionCart(session.getId(), productUUID, quantity);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/cart/remove")
    @ResponseBody
    public String removeFromCart(@RequestParam String cartItemId, HttpSession session) {
        try {
            cartService.removeFromSessionCart(session.getId(), UUID.fromString(cartItemId));
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public String updateQuantity(@RequestParam String cartItemId, 
                               @RequestParam Integer quantity,
                               HttpSession session) {
        try {
            cartService.updateSessionCartQuantity(session.getId(), UUID.fromString(cartItemId), quantity);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/cart/clear")
    @ResponseBody
    public String clearCart(HttpSession session) {
        try {
            cartService.clearSessionCart(session.getId());
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/checkout")
    public String checkout(ModelMap modelMap, HttpSession session) {
        initModelMap(modelMap);
        
        // Check if cart has items
        String sessionId = session.getId();
        int itemCount = cartService.getSessionCartItemCount(sessionId);
        
        if (itemCount == 0) {
            return "redirect:/cart";
        }
        
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Cart").url("/cart").build(),
                Breadcrumb.builder().name("Checkout").url("/checkout").last(true).build()
        ));
        
        List<CartServiceImpl.CartItem> cartItems = cartService.getSessionCartItems(sessionId);
        BigDecimal subtotal = cartService.getSessionCartTotal(sessionId);
        BigDecimal shipping = new BigDecimal("4.00");
        BigDecimal grandTotal = subtotal.add(shipping);
        
        modelMap.addAttribute("cartItems", cartItems);
        modelMap.addAttribute("subtotal", subtotal.toString());
        modelMap.addAttribute("shipping", shipping.toString());
        modelMap.addAttribute("grandTotal", grandTotal.toString());
        
        return "checkout";
    }
}

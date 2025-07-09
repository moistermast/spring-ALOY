package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.dto.ContactFormRequest;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.utils.Breadcrumb;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
public class ContactController extends BaseController {
    public ContactController(ProductService productService, CategoryService categoryService) {
        super(productService, categoryService);
    }

    @GetMapping("/contact")
    public String home(ModelMap modelMap) {
        initModelMap(modelMap);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Contact").url("/contact").last(true).build()
        ));
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@Valid ContactFormRequest request, 
                                  BindingResult bindingResult, 
                                  ModelMap modelMap,
                                  RedirectAttributes redirectAttributes) {
        initModelMap(modelMap);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Contact").url("/contact").last(true).build()
        ));

        if (bindingResult.hasErrors()) {
            // Add form data back to model for re-display
            modelMap.addAttribute("contactForm", request);
            modelMap.addAttribute("errors", bindingResult.getAllErrors());
            return "contact";
        }

        // Process the contact form (send email, save to database, etc.)
        try {
            // TODO: Implement contact form processing
            // contactService.processContactForm(request);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Thank you for your message! We'll get back to you soon.");
            return "redirect:/contact";
        } catch (Exception e) {
            modelMap.addAttribute("contactForm", request);
            modelMap.addAttribute("errorMessage", 
                "Sorry, there was an error sending your message. Please try again.");
            return "contact";
        }
    }
}

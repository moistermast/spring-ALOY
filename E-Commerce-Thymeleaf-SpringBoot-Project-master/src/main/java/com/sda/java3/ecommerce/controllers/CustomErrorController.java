package com.sda.java3.ecommerce.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");
        Object exception = request.getAttribute("javax.servlet.error.exception");
        Object message = request.getAttribute("javax.servlet.error.message");
        Object path = request.getAttribute("javax.servlet.error.request_uri");
        
        model.addAttribute("status", status);
        model.addAttribute("exception", exception);
        model.addAttribute("message", message);
        model.addAttribute("path", path);
        
        System.out.println("ERROR: Status=" + status + ", Exception=" + exception + ", Message=" + message + ", Path=" + path);
        
        return "error";
    }
} 
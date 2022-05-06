package com.example.kimcoffee.controller;

import com.example.kimcoffee.model.Product;
import com.example.kimcoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/products")
    public String productsPage(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }


}

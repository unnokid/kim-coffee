package com.example.kimcoffee.controller;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;
import com.example.kimcoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/products/new")
    public String newProductPage() {
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(CreateProductRequest createProductRequest) {
        productService.createProduct(
                createProductRequest.getName(),
                createProductRequest.getCategory(),
                createProductRequest.getPrice(),
                createProductRequest.getDescription()
        );
        return "redirect:/products";
    }

    @GetMapping("/products/delete")
    public String deleteProductPage() {
        return "delete-product";
    }

    @PostMapping("/products/delete")
    public String deleteProduct(UUIDRequest deleteProductRequest) {
        productService.deleteProduct(deleteProductRequest.getId());
        return "redirect:/products";
    }

    @GetMapping("/products/search")
    public String searchProductPage() {
        return "search-category";
    }

    @PostMapping("/products/search")
    public String searchProductByCategory(Model model, CategoryRequest categoryRequest) {
        List<Product> category = productService.getFindByCategory(Category.valueOf(categoryRequest.getCategory()));
        model.addAttribute("products", category);
        return "product-list";
    }
}

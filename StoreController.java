package com.mabentech.controller;

import com.mabentech.model.Product;
import com.mabentech.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featured", productService.getFeatured());
        model.addAttribute("bestsellers", productService.getBestsellers());
        model.addAttribute("newArrivals", productService.getNewArrivals());
        model.addAttribute("page", "home");
        return "index";
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(required = false) String category,
                       @RequestParam(required = false) String q,
                       Model model) {
        List<Product> products;
        if (q != null && !q.isBlank()) {
            products = productService.search(q);
            model.addAttribute("searchQuery", q);
        } else if (category != null && !category.isBlank()) {
            products = productService.getByCategory(category);
            model.addAttribute("activeCategory", category);
        } else {
            products = productService.getAllActive();
        }
        model.addAttribute("products", products);
        model.addAttribute("page", "shop");
        return "shop";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        return productService.getById(id).map(product -> {
            model.addAttribute("product", product);
            model.addAttribute("related",
                productService.getByCategory(product.getCategory())
                    .stream().filter(p -> !p.getId().equals(id)).limit(4).toList());
            model.addAttribute("page", "product");
            return "product-detail";
        }).orElse("redirect:/shop");
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("page", "about");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("page", "contact");
        return "contact";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("page", "login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("page", "register");
        return "register";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("page", "cart");
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("page", "checkout");
        return "checkout";
    }
}

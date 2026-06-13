package com.mabentech.controller;

import com.mabentech.model.Product;
import com.mabentech.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String category) {
        List<Product> products = category != null
            ? productService.getByCategory(category)
            : productService.getAllActive();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String q) {
        return ResponseEntity.ok(productService.search(q));
    }

    @GetMapping("/products/featured")
    public ResponseEntity<List<Product>> getFeatured() {
        return ResponseEntity.ok(productService.getFeatured());
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(Map.of(
            "status", "operational",
            "app", "MABENTECH",
            "version", "1.0.0",
            "products", productService.countAll()
        ));
    }
}

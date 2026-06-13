package com.mabentech.service;

import com.mabentech.model.Product;
import com.mabentech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllActive() {
        return productRepository.findByActiveTrueOrderByCreatedAtDesc();
    }

    public List<Product> getFeatured() {
        return productRepository.findByFeaturedTrueAndActiveTrue();
    }

    public List<Product> getBestsellers() {
        return productRepository.findTop8ByActiveTrueOrderByReviewCountDesc();
    }

    public List<Product> getByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category);
    }

    public List<Product> getNewArrivals() {
        return productRepository.findTop4ByActiveTrueAndBadgeOrderByCreatedAtDesc("NEW");
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> search(String query) {
        return productRepository.search(query);
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.findById(id).ifPresent(p -> {
            p.setActive(false);
            productRepository.save(p);
        });
    }

    public long countAll() {
        return productRepository.count();
    }
}

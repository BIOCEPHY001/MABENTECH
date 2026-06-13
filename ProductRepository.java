package com.mabentech.repository;

import com.mabentech.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrueOrderByCreatedAtDesc();
    List<Product> findByCategoryAndActiveTrue(String category);
    List<Product> findByFeaturedTrueAndActiveTrue();
    List<Product> findByBadgeAndActiveTrue(String badge);

    @Query("SELECT p FROM Product p WHERE p.active = true AND (" +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.model) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Product> search(@Param("q") String query);

    List<Product> findTop8ByActiveTrueOrderByReviewCountDesc();
    List<Product> findTop4ByActiveTrueAndBadgeOrderByCreatedAtDesc(String badge);
}

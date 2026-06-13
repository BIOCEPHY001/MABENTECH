package com.mabentech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Column(length = 2000)
    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    private BigDecimal oldPrice;

    @Min(0)
    private Integer stock;

    private String imageUrl;

    private String category; // laptop, accessory, gaming, business, student

    private String subcategory; // ultrabook, gaming-laptop, workstation, bag, mouse, keyboard, etc.

    private Double rating;

    private Integer reviewCount;

    private String badge; // NEW, HOT, SALE, BESTSELLER, LIMITED

    private String specs; // JSON string of specs

    private Boolean featured;

    private Boolean active;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) active = true;
        if (featured == null) featured = false;
        if (stock == null) stock = 0;
        if (rating == null) rating = 0.0;
        if (reviewCount == null) reviewCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

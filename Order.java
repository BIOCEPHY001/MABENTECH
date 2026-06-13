package com.mabentech.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    private BigDecimal subtotal;
    private BigDecimal shipping;
    private BigDecimal tax;
    private BigDecimal total;

    private String currency;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Shipping address
    private String shippingName;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private String shippingZip;
    private String shippingPhone;

    private String paymentMethod;
    private String paymentRef;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = OrderStatus.PENDING;
        if (currency == null) currency = "USD";
        orderNumber = "MBT-" + System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum OrderStatus {
        PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED
    }
}

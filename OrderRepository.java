package com.mabentech.repository;

import com.mabentech.model.Order;
import com.mabentech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findAllByOrderByCreatedAtDesc();
    long countByStatus(Order.OrderStatus status);
}

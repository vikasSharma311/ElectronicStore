package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.OrderItem;
import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByProduct(Product product);
}

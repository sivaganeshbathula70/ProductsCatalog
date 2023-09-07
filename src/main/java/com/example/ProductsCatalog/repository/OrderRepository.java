package com.example.ProductsCatalog.repository;


import com.example.ProductsCatalog.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
}

package com.example.Rest.Repository;

import com.example.Rest.POJO.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}

package com.example.Rest.Repository;

import com.example.Rest.POJO.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
}

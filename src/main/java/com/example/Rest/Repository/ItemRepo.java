package com.example.Rest.Repository;

import com.example.Rest.POJO.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Long> {
}

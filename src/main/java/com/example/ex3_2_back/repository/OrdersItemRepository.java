package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OrdersItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "OrdersItemRepository")
public interface OrdersItemRepository extends JpaRepository<OrdersItem, Integer> {


}

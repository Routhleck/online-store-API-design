package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Orders;
import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "OrderRepository")
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Operation(summary = "通过用户id查找订单")
    @RestResource(path = "findByUserId")
    List<Orders> findByUserId(int userId);

}

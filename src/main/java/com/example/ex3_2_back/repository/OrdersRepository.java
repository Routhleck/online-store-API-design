package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Orders;
import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "OrderRepository")
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Operation(summary = "通过用户id查找订单")
    @RestResource(path = "findByUserId")
    List<Orders> findByUserId(int userId);

    @Operation(summary = "通过订单id查找订单")
    @RestResource(path = "findByOrdersId")
    Orders findById(int ordersId);

    @Operation(summary = "重置id")
    @Modifying
    @Transactional
    @Query(value = "update orders_seq set next_val = 1", nativeQuery = true)
    @RestResource(path = "resetId")
    void resetId();
}

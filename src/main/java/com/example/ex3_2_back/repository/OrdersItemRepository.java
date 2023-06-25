package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Orders;
import com.example.ex3_2_back.entity.OrdersItem;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(path = "OrdersItemRepository")
public interface OrdersItemRepository extends JpaRepository<OrdersItem, Integer> {


    @Operation(summary = "通过订单查找订单商品详情")
    @Transactional
    @Query(value = "select product_id, quantity from orders_item where orders_id = :ordersId", nativeQuery = true)
    @RestResource(path = "findByOrdersId")
    List<Map<String, Object>> findByOrdersId(int ordersId);

    @Operation(summary = "通过订单id删除订单商品")
    @Transactional
    @Modifying
    @Query(value = "delete from orders_item where orders_id = :ordersId", nativeQuery = true)
    @RestResource(path = "deleteByOrdersId")
    void deleteByOrdersId(int ordersId);

    @Operation(summary = "重置id")
    @Transactional
    @Modifying
    @Query(value = "update orders_item_seq set next_val = 1", nativeQuery = true)
    @RestResource(path = "resetId")
    void resetId();

    @Operation(summary = "通过订单id查找订单商品详情")
    @RestResource(path = "findDetailsByOrderId")
    @Query("select p ,ot.quantity from Product p,OrdersItem ot where p.id=ot.product.id and ot.orders.id = :ordersId")
    List<Object> findDetailsByOrderId(int ordersId);
}

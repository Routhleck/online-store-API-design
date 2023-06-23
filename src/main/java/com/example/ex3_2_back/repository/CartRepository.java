package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.CartItem;
import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "CartRepository")
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Operation(summary = "通过用户查找购物车")
    @RestResource(path = "findByUser")
    Cart findByUser(User user);

    @Operation(summary = "通过用户id创建购物车")
    @RestResource(path = "insertByUserId")
    @Query(value = "insert into cart (user_id) values (?1)", nativeQuery = true)
    void insertByUserId(Integer id);

//    Optional<Cart> findById(int id);

//    List<CartItem> findItemsById(int id);
}

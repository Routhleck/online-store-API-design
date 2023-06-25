package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.CartItem;
import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "CartRepository")
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Operation(summary = "通过用户查找购物车")
    @RestResource(path = "findByUser")
    Cart findByUser(User user);



    @Operation(summary = "通过用户id删除购物车")
    @RestResource(path = "deleteByUserId")
    @Modifying
    @Transactional
    @Query(value = "delete from cart where user_id = :userId", nativeQuery = true)
    void deleteByUserId(Integer userId);

    @Operation(summary = "通过用户id查找购物车")
    @RestResource(path = "findByUserId")
    @Query(value = "select * from cart where user_id = :userId", nativeQuery = true)
//    @Query("select p from Product p,CartItem ct, Cart c where p.id=ct.product.id and ct.cart.id=c.id and c.user.id = :userId")
    Cart findByUserId(int userId);



//    Optional<Cart> findById(int id);

//    List<CartItem> findItemsById(int id);
}

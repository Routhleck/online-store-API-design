package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.CartItem;
import com.example.ex3_2_back.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "CartItemRepository")
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Operation(summary = "通过购物车查找条目")
    @RestResource(path = "findByCart")
    List<CartItem> findByCart(Cart cart);

    @Operation(summary = "通过购物车和商品查找条目")
    @RestResource(path = "findByCartAndProduct")
    CartItem findByCartAndProduct(Cart cart, Product product);
}

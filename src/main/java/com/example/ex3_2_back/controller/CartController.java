package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.repository.CartRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartRepository cartRepository;

    @Autowired
    @Operation(summary = "setCartRepository", description = "setCartRepository")
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Operation(summary = "通过用户id查找购物车")
    @GetMapping("/findByUserId")
    public Cart findByUserId(int userId) {
        return cartRepository.findByUserId(userId);
    }
}

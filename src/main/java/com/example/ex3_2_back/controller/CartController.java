package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartRepository cartRepository;

    @Autowired
    public void setCartRepository(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }
}

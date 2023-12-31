package com.example.ex3_2_back.repository;


import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.CartItem;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CartItemRepositoryTests {
    @Resource
    CartItemRepository cartItemRepository;

    @Test
    void insertByCartAndProduct(){
        cartItemRepository.insertByCartAndProduct(1,1,1);
    }

    @Test
    void findByCartId(){
//        List<CartItem> cartItems = cartItemRepository.findByCartId(1);
//        System.out.println(cartItems);
    }

    @Test
    void findByCartIdAndProductId(){
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(1,1);
        System.out.println(cartItem);
    }

    @Test
    void updateById(){
//        cartItemRepository.updateById(1,1,3);
    }

    @Test
    void deleteById(){
//        cartItemRepository.deleteById(1,1);
    }

}

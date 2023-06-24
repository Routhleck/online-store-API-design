package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordersItem")
public class OrdersItemController {

    private OrdersRepository ordersRepository;

    private OrdersItemRepository ordersItemRepository;

    private CartItemRepository cartItemRepository;
    private CartController cartRepository;

    private ShopRepository shopRepository;

    private CategoryRepository categoryRepository;

    private ProductController productRepository;

    @Autowired
    @Operation(summary = "setOrdersRepository", description = "setOrdersRepository")
    public void setOrdersRepository(OrdersRepository ordersRepository){
        this.ordersRepository = ordersRepository;
    }

    @Autowired
    @Operation(summary = "setOrdersItemRepository", description = "setOrdersItemRepository")
    public void setOrdersItemRepository(OrdersItemRepository ordersItemRepository){
        this.ordersItemRepository = ordersItemRepository;
    }

    @Autowired
    @Operation(summary = "serCartItemRepository", description = "serCartItemRepository")
    public void setProductRepository(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }

    @Autowired
    @Operation(summary = "serCartRepository", description = "serCartRepository")
    public void setCartRepository(CartController cartRepository){
        this.cartRepository = cartRepository;
    }

    @Autowired
    @Operation(summary = "serProductRepository", description = "serProductRepository")
    public void setProductRepository(ProductController productRepository){
        this.productRepository = productRepository;
    }

    @Autowired
    @Operation(summary = "serShopRepository", description = "serShopRepository")
    public void setShopRepository(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    @Autowired
    @Operation(summary = "serCategoryRepository", description = "serCategoryRepository")
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }



}

package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.Orders;
import com.example.ex3_2_back.entity.OrdersItem;
import com.example.ex3_2_back.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/findByOrdersId")
    @Operation(summary = "通过订单ID展示订单商品列表",description = "通过订单ID展示订单商品列表")
    public Result findByOrdersId(@RequestParam("orders_id") int ordersId){
        Orders orders = ordersRepository.findById(ordersId);
        if(orders == null){
            return Result.error("订单不存在");
        }
        List<Map<String, Object>> ordersItemList = ordersItemRepository.findByOrdersId(ordersId);
        return Result.success(ordersItemList);
    }


    @Operation(summary = "通过订单ID删除订单商品列表",description = "通过订单ID删除订单商品列表")
    public Result deleteByOrdersId(@RequestParam("orders_id") int ordersId){
        Orders orders = ordersRepository.findById(ordersId);
        if(orders == null){
            return Result.error("订单不存在");
        }
        ordersItemRepository.deleteByOrdersId(ordersId);
        return Result.success();
    }


    @Operation(summary = "删除所有订单商品列表",description = "删除所有订单商品列表")
    public Result deleteAll(){
        ordersItemRepository.deleteAll();
        return Result.success();
    }

    @GetMapping("/findDetailsByOrderId")
    @Operation(summary = "通过订单ID查找订单商品详情",description = "通过订单ID查找订单商品详情")
    public Result findDetailsByUserId(@RequestParam("orders_id") int ordersId){
        List<Object> orderItemsDetails = ordersItemRepository.findDetailsByOrderId(ordersId);
        // 判断是否为空
        if(orderItemsDetails.isEmpty()){
            return Result.error("购物车为空");
        }
        return Result.success(orderItemsDetails);
    }


}

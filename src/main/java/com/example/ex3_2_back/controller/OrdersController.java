package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;

    private OrdersItemRepository ordersItemRepository;

    private CartItemRepository cartItemRepository;

    private CartController cartRepository;

    private ProductController productRepository;

    private UserRepository userRepository;

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
    @Operation(summary = "serUserRepository", description = "serUserRepository")
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Operation(summary = "通过用户id查找订单")
    @GetMapping("/findByUserId")
    public List<Orders> findByUserId(int userId) {
        return ordersRepository.findByUserId(userId);
    }


    @PostMapping("/createFromCart")
    @Operation(summary = "通过用户ID创建订单", description = "通过用户ID创建订单")
    public Result createFromCart(@RequestParam("user_id") int userId) {
        Optional<User> user = userRepository.findById(String.valueOf(userId));

        // 判断是否为空
        if(user.isEmpty()){
            return Result.error("用户不存在");
        }

        // 查询对应user的cart
        Cart cart = cartRepository.findByUserId(userId);

        // 获取cartItem
        List<Map<String, Object>> cartItems = cartItemRepository.findByCartId(cart.getId());

        // 判断是否为空
        if(cartItems == null){
            return Result.error("购物车为空");
        }

        // 创建订单
        Orders orders = new Orders();
        orders.setUser(user.get());
        orders.setIsPayed(false);
        orders.setIsDelivered(false);
        orders.setIsFinished(false);

        // 保存订单
        ordersRepository.save(orders);

        // 将cartItem插入到ordersItem
        for(Map<String, Object> cartItem : cartItems){
            OrdersItem ordersItem = new OrdersItem();
            ordersItem.setOrders(orders);
            Optional<Product> product = productRepository.findById(Integer.parseInt(cartItem.get("product_id").toString()));
            if(product.isEmpty()){
                continue;
            }

            ordersItem.setProduct(product.get());
            ordersItem.setQuantity(Integer.parseInt(cartItem.get("quantity").toString()));
            ordersItemRepository.save(ordersItem);
        }

        // 清空购物车
        cartItemRepository.deleteByCartId(cart.getId());

        return Result.success("创建订单成功");
    }
}

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


    @GetMapping("/findByUserId")
    @Operation(summary = "通过用户ID展示订单商品列表",description = "通过用户ID展示订单商品列表")
    public Result findByUserIdResult(@RequestParam("user_id") int userId){
        List<Orders> orders = ordersRepository.findByUserId(userId);

        if (orders.isEmpty()){
            return Result.error("该用户没有订单");
        }
        else{
            return Result.success(orders);
        }
    }

    @Operation(summary = "通过用户id查找订单")
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

    @DeleteMapping("/deleteById")
    @Operation(summary = "通过订单ID删除订单", description = "通过订单ID删除订单")
    public Result deleteById(@RequestParam("orders_id") int id) {
        Optional<Orders> orders = Optional.ofNullable(ordersRepository.findById(id));

        // 判断是否为空
        if(orders.isEmpty()){
            return Result.error("订单不存在");
        }

        // 删除订单商品
        ordersItemRepository.deleteByOrdersId(id);

        // 删除订单
        ordersRepository.deleteById(id);

        return Result.success("删除订单成功");
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "删除所有订单", description = "删除所有订单")
    public Result deleteAll() {
        ordersRepository.deleteAll();
        ordersRepository.resetId();
        ordersItemRepository.deleteAll();
        ordersItemRepository.resetId();
        return Result.success("删除所有订单成功");
    }

    @PutMapping("/updateIsPayed")
    @Operation(summary = "修改订单支付状态为true", description = "修改订单支付状态为true")
    public Result updateIsPayed(@RequestParam("orders_id") int id) {
        Optional<Orders> orders = Optional.ofNullable(ordersRepository.findById(id));

        // 判断是否为空
        if(orders.isEmpty()){
            return Result.error("订单不存在");
        }

        // 判断订单是否发货
        if(orders.get().getIsDelivered()){
            return Result.error("订单已发货，无法修改支付状态");
        }

        // 判断订单是否完成
        if(orders.get().getIsFinished()){
            return Result.error("订单已完成，无法修改支付状态");
        }

        // 修改订单支付状态
        orders.get().setIsPayed(true);
        ordersRepository.save(orders.get());

        return Result.success("修改订单支付状态成功");
    }

    @PutMapping("/updateIsDelivered")
    @Operation(summary = "修改订单发货状态为true", description = "修改订单发货状态为true")
    public Result updateIsDelivered(@RequestParam("orders_id") int id) {
        Optional<Orders> orders = Optional.ofNullable(ordersRepository.findById(id));

        // 判断是否为空
        if(orders.isEmpty()){
            return Result.error("订单不存在");
        }

        // 判断订单是否支付
        if(!orders.get().getIsPayed()){
            return Result.error("订单未支付，无法修改发货状态");
        }

        // 判断订单是否完成
        if(orders.get().getIsFinished()){
            return Result.error("订单已完成，无法修改发货状态");
        }

        // 修改订单发货状态
        orders.get().setIsDelivered(true);
        ordersRepository.save(orders.get());

        return Result.success("修改订单发货状态成功");
    }

    @PutMapping("/updateIsFinished")
    @Operation(summary = "修改订单完成状态为true", description = "修改订单完成状态为true")
    public Result updateIsFinished(@RequestParam("orders_id") int id) {
        Optional<Orders> orders = Optional.ofNullable(ordersRepository.findById(id));

        // 判断是否为空
        if (orders.isEmpty()) {
            return Result.error("订单不存在");
        }

        // 判断订单是否支付
        if (!orders.get().getIsPayed()) {
            return Result.error("订单未支付，无法修改完成状态");
        }

        // 判断订单是否发货
        if (!orders.get().getIsDelivered()) {
            return Result.error("订单未发货，无法修改完成状态");
        }

        // 修改订单完成状态
        orders.get().setIsFinished(true);
        ordersRepository.save(orders.get());

        return Result.success("修改订单完成状态成功");
    }

}

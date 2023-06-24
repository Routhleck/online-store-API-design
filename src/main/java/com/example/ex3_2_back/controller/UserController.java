package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.CartItem;
import com.example.ex3_2_back.entity.User;
import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.repository.CartRepository;
import com.example.ex3_2_back.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;
    private CartRepository cartRepository;

    @Autowired
    @Operation(summary = "setUserRepository", description = "setUserRepository")
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Autowired
    @Operation(summary = "setCartRepository", description = "setCartRepository")
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping("/findAll")
    @Operation(summary = "查询所有用户", description = "查询所有用户")
    public Result findAll() {
        return Result.success(userRepository.findAll());
    }


    @GetMapping("/findById")
    @Operation(summary = "根据Id查询单个用户", description = "根据Id查询单个用户")
    public Result findById(@RequestParam("id") int id) {
        return Result.success(userRepository.findById(String.valueOf(id)));
    }

    @GetMapping("/findByName")
    @Operation(summary = "根据name查询单个用户", description = "根据name查询单个用户")
    public Result findByName(@RequestParam("name") String name) {
        return Result.success(userRepository.findByName(name));
    }

    @PostMapping("/new")
    @Operation(summary = "创建用户", description = "创建用户")
    public Result create(@RequestBody User user) {
        try {
            userRepository.save(user);
            // 为用户创建对应购物车id的cart
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/deleteById")
    @Operation(summary = "根据Id删除用户", description = "根据Id删除用户")
    public Result deleteById(@RequestParam("id") int id) {
        try {
            cartRepository.deleteByUserId(id);
            userRepository.deleteById(String.valueOf(id));
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "删除所有用户", description = "删除所有用户")
    public Result deleteAll() {
        try {
            userRepository.deleteAll();
            userRepository.resetId();
            cartRepository.deleteAll();
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户", description = "更新用户")
    public Result update(@RequestParam("id") int id,
                         @RequestParam("name") String name,
                         @RequestParam("gender") Integer gender,
                         @RequestParam("password") String password,
                         @RequestParam("phone") String phone,
                         @RequestParam("address") String address,
                         @RequestParam("email") String email,
                         @RequestParam("token") String token) {
        try {
            userRepository.update(id, name, gender, password, phone, address, email, token);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

}

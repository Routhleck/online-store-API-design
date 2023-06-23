package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.CartItem;
import com.example.ex3_2_back.repository.CartItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
    private CartItemRepository cartItemRepository;

    @Autowired
    @Operation(summary = "serCartItemRepository", description = "serCartItemRepository")
    public void setProductRepository(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping("/findById")
    @Operation(summary = "展示购物车商品列表",description = "展示购物车商品列表")
    public Result findById(@RequestParam("cartId") int cartId){
        return Result.success(cartItemRepository.findByCartId(cartId));
    }

    @GetMapping("/findOne")
    @Operation(summary = "根据购物车id和商品id查看信息",description = "根据购物车id和商品id查看信息")
    public Result findOne(@RequestParam("cartId") int cartId,
                          @RequestParam("productId") int productId){
        return Result.success(cartItemRepository.findByCartIdAndProductId(cartId,productId));
    }

    @PostMapping("/insert")
    @Operation(summary = "向购物车内添加商品",description = "向购物车内添加商品")
    public Result insertProduct(@RequestParam("cartId") int cartId,
                                @RequestParam("productId") int productId,
                                @RequestParam("quantity") int quantity){
        try{
            cartItemRepository.insertByCartAndProduct(cartId,productId,quantity);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PostMapping("/delete")
    @Operation(summary = "删除购物车内的商品",description = "删除购物车内的商品")
    public Result deleteProduct(@RequestParam("cartId") int cartId,
                                @RequestParam("productId") int productId){
        try{
            cartItemRepository.deleteById(cartId,productId);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PostMapping("/update")
    @Operation(summary = "更新购物车内商品数量",description = "更新购物车内商品数量")
    public Result updateProduct(@RequestParam("cartId") int cartId,
                                @RequestParam("productId") int productId,
                                @RequestParam("quantity") int quantity){
        try{
            cartItemRepository.updateById(cartId,productId,quantity);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

}

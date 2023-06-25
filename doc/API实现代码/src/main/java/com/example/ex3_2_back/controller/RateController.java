package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.entity.Rate;
import com.example.ex3_2_back.entity.User;
import com.example.ex3_2_back.repository.ProductRepository;
import com.example.ex3_2_back.repository.RateRepository;
import com.example.ex3_2_back.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/rate")
public class RateController {

    private RateRepository rateRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    @Autowired
    @Operation(summary = "setRateRepository", description = "setRateRepository")
    public void setRateRepository(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Autowired
    @Operation(summary = "setUserRepository", description = "setUserRepository")
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    @Operation(summary = "setProductRepository", description = "setProductRepository")
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/findAll")
    @Operation(summary = "查找所有评分", description = "查找所有评分")
    public Result findAll() {
        if (rateRepository.findAll().isEmpty()) {
            return Result.error("评分为空");
        }
        else {
            return Result.success(rateRepository.findAll());
        }
    }

    @GetMapping("/findByUserId")
    @Operation(summary = "通过用户id查找评分", description = "通过用户id查找评分")
    public Result findByUserId(@RequestParam("user_id") int userId) {
        Optional<User> user = userRepository.findById(String.valueOf(userId));

        if (user.isEmpty()) {
            return Result.error("用户不存在");
        }

        return Result.success(rateRepository.findByUserId(userId));
    }

    @PostMapping("/new")
    @Operation(summary = "通过用户ID, 商品ID和评分创建新的评分", description = "通过用户ID, 商品ID和评分创建新的评分")
    public Result newRate(@RequestParam("user_id") int userId,
                          @RequestParam("product_id") int productId,
                          @RequestParam("rate") BigDecimal rateValue) {
        Optional<User> user = userRepository.findById(String.valueOf(userId));

        if (user.isEmpty()) {
            return Result.error("用户不存在");
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            return Result.error("商品不存在");
        }

        BigDecimal minValue = new BigDecimal("0");
        BigDecimal maxValue = new BigDecimal("5");

        if (rateValue.compareTo(minValue) < 0 || rateValue.compareTo(maxValue) > 0) {
            return Result.error("评分不合法");
        }

        Rate rate = new Rate();
        rate.setUser(user.get());
        rate.setProduct(product.get());
        rate.setRate(rateValue);

        // 判断是否已经存在userId和productId相同的评分
        if (rateRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            return Result.error("评分已存在");
        }

        rateRepository.save(rate);

        rateRepository.updateProductRating(productId);

        return Result.success("评分成功");
    }

    @PutMapping("/update")
    @Operation(summary = "通过用户ID, 商品ID和评分更新评分", description = "通过用户ID, 商品ID和评分更新评分")
    public Result updateRate(@RequestParam("user_id") int userId,
                             @RequestParam("product_id") int productId,
                             @RequestParam("rate") BigDecimal rateValue) {
        Optional<User> user = userRepository.findById(String.valueOf(userId));

        if (user.isEmpty()) {
            return Result.error("用户不存在");
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            return Result.error("商品不存在");
        }

        BigDecimal minValue = new BigDecimal("0");
        BigDecimal maxValue = new BigDecimal("5");

        if (rateValue.compareTo(minValue) < 0 || rateValue.compareTo(maxValue) > 0) {
            return Result.error("评分不合法");
        }

        Optional<Rate> rate = rateRepository.findByUserIdAndProductId(userId, productId);

        if (rate.isEmpty()) {
            return Result.error("评分不存在");
        }

        rate.get().setRate(rateValue);
        rateRepository.save(rate.get());

        rateRepository.updateProductRating(productId);

        return Result.success("评分更新成功");
    }


    @DeleteMapping("/deleteAll")
    @Operation(summary = "删除所有评分", description = "删除所有评分")
    public Result deleteAll() {
        rateRepository.deleteAll();
        return Result.success("删除成功");
    }

    @DeleteMapping("/deleteById")
    @Operation(summary = "通过评分ID删除评分", description = "通过评分ID删除评分")
    public Result deleteById(@RequestParam("rate_id") int rateId) {
        Optional<Rate> rate = rateRepository.findById(rateId);

        if (rate.isEmpty()) {
            return Result.error("评分不存在");
        }

        rateRepository.deleteById(rateId);
        return Result.success("删除成功");
    }
}

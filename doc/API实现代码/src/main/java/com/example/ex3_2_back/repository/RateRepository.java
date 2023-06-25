package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Rate;
import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "RateRepository")
public interface RateRepository extends JpaRepository<Rate, Integer> {


    @Operation(summary = "更新商品评分")
    @Transactional
    @Modifying
    @Query(value = "UPDATE Product p SET p.rate = (SELECT AVG(r.rate) FROM Rate r WHERE r.product = p) WHERE p.id = :productId")
    @RestResource(path = "updateProductRating")
    void updateProductRating(@Param("productId") Integer productId);

    @Operation(summary = "通过用户id查找评分")
    @Transactional
    @Query(value = "select * from rate where user_id = :userId", nativeQuery = true)
    @RestResource(path = "findByUserId")
    List<Rate> findByUserId(@Param("userId") Integer userId);

    @Operation(summary = "查询是否已经存在userId和productId相同的评分")
    @Transactional
    @Query(value = "select * from rate where user_id = :userId and product_id = :productId", nativeQuery = true)
    Optional<Rate> findByUserIdAndProductId(int userId, int productId);
}

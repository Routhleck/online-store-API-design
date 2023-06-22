package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Operation(summary = "通过产品名称查找")
    @RestResource(path = "findByName")
    Optional<Product> findByName(String name);

    @Transactional
    @Operation(summary = "通过商品名删除商品")
    @RestResource(path = "deleteByName")
    void deleteByName(String name);

    @Operation(summary = "通过用户名模糊查询")
    @RestResource(path = "findAllByNameLike")
    List<Product> findAllByNameLike(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Product SET description=?1 , price=?2 WHERE name=?3")
    @Operation(summary = "通过商品名修改商品信息")
    @RestResource(path = "updateInfoByName")
    int updateInfoByName(String description, BigDecimal price, String name);

    @Transactional
    @Modifying
    @Query("UPDATE Product SET isList=?1 WHERE name=?2")
    @Operation(summary = "商品上架和下架")
    @RestResource(path = "updateIsListByName")
    int updateIsListByName(boolean isList, String name);

}

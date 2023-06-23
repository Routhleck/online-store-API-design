package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Shop;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "ShopRepository")
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Operation(summary = "通过id查找商店")
    @Transactional
    @Query(value = "select * from shop where id = :id", nativeQuery = true)
    @RestResource(path = "findById")
    Shop findById(int id);

    @Operation(summary = "查找所有商店")
    @Transactional
    @Query(value = "select * from shop", nativeQuery = true)
    @RestResource(path = "findAll")
    List<Shop> findAll();

    @Operation(summary = "通过id删除商店")
    @Modifying
    @Transactional
    @Query(value = "delete from shop where id = :id", nativeQuery = true)
    @RestResource(path = "deleteById")
    void deleteById(int id);

    @Operation(summary = "通过id更新商店")
    @Transactional
    @Modifying
    @Query("UPDATE Shop s set s.shopName = :shopName where s.id = :id")
    @RestResource(path = "updateById")
    void updateById(int id,String shopName);

    @Operation(summary = "添加商店")
    @Modifying
    @Transactional
    @Query(value="insert into shop(shop_name) values(:shopName)", nativeQuery = true)
    @RestResource(path = "insert")
    void insert(String shopName);

    @Operation(summary = "通过商店名查找商店")
    @Modifying
    @Transactional
    @Query(value = "select * from shop where shop_name = :s", nativeQuery = true)
    @RestResource(path = "findByShopName")
    Shop findByShopName(String s);

    @Operation(summary = "通过商店名判断商店是否存在")
    @RestResource(path = "existsByShopName")
    boolean existsByShopName(String shopName);
}

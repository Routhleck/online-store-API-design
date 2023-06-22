package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Cart;
import com.example.ex3_2_back.entity.CartItem;
import com.example.ex3_2_back.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "CartItemRepository")
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Operation(summary = "通过购物车查找条目")
    @Modifying
    @Transactional
    @Query(value = "select * from cart_item where cart_id = :cartId", nativeQuery = true)
    @RestResource(path = "findByCartId")
    List<CartItem> findByCartId(int cartId);

    @Operation(summary = "通过购物车id和商品id查找条目")
    @Transactional
    @Query(value = "select * from cart_item where cart_id = :cartId and product_id = :productId", nativeQuery = true)
    @RestResource(path = "findByCartIdAndProductId")
    CartItem findByCartIdAndProductId(int cartId,int productId);

    @Operation(summary = "通过购物车和商品添加商品")
    @Modifying
    @Transactional
    @Query(value="insert into cart_item(cart_id,product_id,quantity) values(:cartId,:productId,:quantity)", nativeQuery = true)
    @RestResource(path = "insertByCartAndProduct")
    void insertByCartAndProduct(int cartId,int productId,int quantity);

    @Operation(summary = "通过id删除商品")
    @RestResource(path = "deleteById")
    void deleteById(int Id);

    @Operation(summary = "通过id更新商品数量")
    @Transactional
    @Modifying
    @Query("UPDATE CartItem c set c.quantity = :quantity where c.product.id = :productId")
    @RestResource(path = "updateById")
    void updateById(int productId,int quantity);


}

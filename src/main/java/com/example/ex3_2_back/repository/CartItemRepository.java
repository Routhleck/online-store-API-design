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
import java.util.Map;

@RepositoryRestResource(path = "CartItemRepository")
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Operation(summary = "通过购物车查找条目")
    @Modifying
    @Transactional
    @Query(value = "select product_id, quantity from cart_item where cart_id = :cartId", nativeQuery = true)
    @RestResource(path = "findByCartId")
    List<Map<String, Object>> findByCartId(int cartId);

    @Operation(summary = "通过用户id查找购物车及详情")
    @RestResource(path = "findDetailsByUserId")
    @Query("select p from Product p,CartItem ct , Cart c where p.id=ct.product.id and ct.cart.id=c.id and c.user.id = :userId")
    List<Object> findDetailsByUserId(int userId);

    @Operation(summary = "通过用户id、店铺id和类别id查找购物车及详情")
    @RestResource(path = "findDetailsByUserIdShopIdCategoryId")
    @Query("select p from Product p,CartItem ct , Cart c where p.id=ct.product.id and ct.cart.id=c.id and c.user.id = :userId and p.shop.id = :shopId and p.category.id = :categoryId")
    List<Object> findDetailsByUserIdShopIdCategoryId(int userId, int shopId, int categoryId);

    @Operation(summary = "通过用户id、店铺id、分类id查找购物车中的商品")
    @Transactional
    @Query(value = "select product_id, quantity from cart_item where cart_id = :cartId and product_id in (select id from product where shop_id = :shopId and category_id = :categoryId)", nativeQuery = true)
    List<Map<String, Object>> findByCartIdShopIdCategoryId(int cartId, int shopId, int categoryId);


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
    @Modifying
    @Transactional
    @Query(value = "delete from cart_item where cart_id = :cartId and product_id = :productId", nativeQuery = true)
    @RestResource(path = "deleteById")
    void deleteByIdProductId(int cartId,int productId);

    @Operation(summary = "通过id更新商品数量")
    @Transactional
    @Modifying
    @Query("UPDATE CartItem c set c.quantity = :quantity where c.product.id = :productId and c.cart.id = :cartId")
    @RestResource(path = "updateById")
    void updateByIdProductIdQuantity(int cartId,int productId,int quantity);


    @Operation(summary = "通过购物车id、店铺id删除购物车中的商品")
    @Transactional
    @Modifying
    @Query(value = "delete from cart_item where cart_id = :cartId and product_id in (select id from product where shop_id = :shopId)", nativeQuery = true)
    @RestResource(path = "deleteByUserIdShopId")
    void deleteByIdShopId(Integer cartId, Integer shopId);

    @Operation(summary = "通过购物车id、分类id删除购物车中的商品")
    @Transactional
    @Modifying
    @Query(value = "delete from cart_item where cart_id = :cartId and product_id in (select id from product where category_id = :categoryId)", nativeQuery = true)
    @RestResource(path = "deleteByUserIdCategoryId")
    void deleteByIdCategoryId(Integer cartId, Integer categoryId);

    @Operation(summary = "通过购物车id删除购物车中的商品")
    @Transactional
    @Modifying
    @Query(value = "delete from cart_item where cart_id = :cartId", nativeQuery = true)
    @RestResource(path = "deleteByCartId")
    void deleteByCartId(Integer cartId);


}

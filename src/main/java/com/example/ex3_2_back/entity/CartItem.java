package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;

/**
 * @program: online-store-API-design
 * @description: 购物车与商品关系类
 * @author: 20301037_Routhleck
 * @create: 2023-06-20 15:11
 **/

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CartItem")
@Schema(description = "CartItem")
@Proxy(lazy = false)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(columnDefinition = "int comment '商品数量' default 1")
    Integer quantity;
}

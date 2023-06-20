package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
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
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    Cart cart;

    @ManyToOne
    @JoinColumn(name="orders_id", referencedColumnName="id")
    Orders orders;

    @ManyToOne
    Product product;

    @Column(columnDefinition = "int comment '商品数量' default 1")
    Integer quantity;
}

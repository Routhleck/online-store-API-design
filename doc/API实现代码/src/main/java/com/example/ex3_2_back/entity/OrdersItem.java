package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
/**
 * @program: online-store-API-design
 * @description: 订单与商品关系类
 * @author: 20301037_Routhleck
 * @create: 2023-06-20 18:13
 **/

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders_Item")
@Schema(description = "OrdersItem")
public class OrdersItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name="orders_id", referencedColumnName="id")
    Orders orders;

    @ManyToOne
    Product product;

    @Column(columnDefinition = "int comment '商品数量' default 1")
    Integer quantity;
}

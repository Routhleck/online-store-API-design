package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
/**
 * @program: online-store-API-design
 * @description: 商品类
 * @author: 20301037_Routhleck
 * @create: 2023-06-20 15:05
 **/

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Product")
@Schema(description = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '商品名称' default 'test'")
    String name;

    @Column(columnDefinition = "varchar(255) comment '商品描述' default 'test'")
    String description;

    @Column(columnDefinition = "int comment '商品价格' default '0'")
    @Builder.Default
    BigDecimal price = BigDecimal.ZERO;
}

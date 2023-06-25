package com.example.ex3_2_back.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shop")
@Schema(description = "Shop")
@Proxy(lazy = false)
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(columnDefinition = "varchar(255) comment '店铺名称' default 'test'")
    String shopName;
}

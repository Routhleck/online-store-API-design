package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.List;
/**
 * @program: online-store-API-design
 * @description: 购物车类
 * @author: 20301037_Routhleck
 * @create: 2023-06-20 15:06
 **/
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cart")
@Schema(description = "Cart")
@Proxy(lazy = false)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @OneToOne
    User user;

}

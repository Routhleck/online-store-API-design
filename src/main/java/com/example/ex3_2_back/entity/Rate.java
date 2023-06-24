package com.example.ex3_2_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "rate")
@Schema(description = "Rate")
@Proxy(lazy = false)
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @OneToOne
    Product product;

    @OneToOne
    User user;

    @Column(columnDefinition = "decimal comment '商品评分' default 0.0")
    @Builder.Default
    BigDecimal rate = BigDecimal.ZERO;
}

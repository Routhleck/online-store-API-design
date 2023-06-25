package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
/**
 * @program: online-store-API-design
 * @description: 订单类
 * @author: 20301037_Routhleck
 * @create: 2023-06-20 15:07
 **/
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
@Schema(description = "Order")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @OneToOne
    User user;

    @Column(columnDefinition = "boolean comment '是否支付' default false")
    Boolean isPayed;

    @Column(columnDefinition = "boolean comment '是否发货' default false")
    Boolean isDelivered;

    @Column(columnDefinition = "boolean comment '是否完成' default false")
    Boolean isFinished;

}

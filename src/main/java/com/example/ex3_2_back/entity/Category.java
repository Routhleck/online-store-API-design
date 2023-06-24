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
@Table(name = "category")
@Schema(description = "Category")
@Proxy(lazy = false)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(columnDefinition = "varchar(255) comment '分类名称' default 'test'")
    String categoryName;
}

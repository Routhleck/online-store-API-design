package com.example.ex3_2_back.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
@Schema(description = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '用户名' default 'test'  ")
    String name;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '密码' default 'test'")
    String password;

    @Column(nullable = false, columnDefinition = "integer(1) comment '性别'")
    @Builder.Default
    @Convert(converter = GenderConverter.class)
    Gender gender = Gender.Unknown;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '邮箱' default 'test@qq.com'")
    @Email
    String email;

    @Column(nullable = false, columnDefinition = "varchar(32) comment '手机号' default '111111111111'")
    String phone;

    @Column(nullable = true, columnDefinition = "varchar(255) comment '地址' default 'test'")
    String address;

    @Column(nullable = false, columnDefinition = "varchar(255) comment 'Token' default ''  ")
    String token;

}

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
@Table(name = "t_User")
@Schema(description = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(unique = true, nullable = false)
    @Schema(defaultValue = "test")
    String name;
    @Schema(defaultValue = "test")
    String password;
    @Builder.Default
    Gender gender = Gender.Unknown;
    @Email
    @Schema(defaultValue = "test@qq.com")
    String email;
    @Schema(defaultValue = "111111111111")
    String phone;
    @Schema(defaultValue = "test")
    String address;
}

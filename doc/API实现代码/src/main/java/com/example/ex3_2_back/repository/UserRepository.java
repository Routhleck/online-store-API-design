package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;


@RepositoryRestResource(path = "UserRepository")
@Tag(name = "数据库User接口")
public interface UserRepository extends JpaRepository<User, String> {
    @Operation(summary = "通过用户名查找")
    @RestResource(path = "findByName")
    Optional<User> findByName(String name);

    @Operation(summary = "通过用户名和密码查找出一个用户")
    @RestResource(path = "findByNameAndPassword")
    Optional<User> findByNameAndPassword(String name, String password);

    @Operation(summary = "是否存在名字")
    @RestResource(path = "existsByName")
    boolean existsByName(String name);

    @Operation(summary = "是否存在用户名和密码")
    @RestResource(path = "existsByNameAndPassword")
    boolean existsByNameAndPassword(String name, String password);

    @Operation(summary = "通过用户名删除用户")
    @RestResource(path = "deleteByName")
    void deleteByName(String name);

    @Operation(summary = "通过id更新用户")
    @RestResource(path = "update")
    @Modifying
    @Transactional
    @Query("UPDATE User SET name = :name, gender = :gender, password = :password, phone = :phone, address = :address, email = :email WHERE id = :id")
    void update(int id, String name, Integer gender, String password, String phone, String address, String email);


    @Operation(summary = "重置用户id递增")
    @RestResource(path = "resetId")
    @Modifying
    @Transactional
    @Query(value = "update user_seq set next_val = 1", nativeQuery = true)
    void resetId();
}

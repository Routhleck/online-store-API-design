package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "CategoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Operation(summary = "通过id查找商店")
    @Transactional
    @Query(value = "select * from category where id = :id", nativeQuery = true)
    @RestResource(path = "findById")
    Category findById(int id);

    @Operation(summary = "查找所有商店")
    @Transactional
    @Query(value = "select * from category", nativeQuery = true)
    @RestResource(path = "findAll")
    List<Category> findAll();

    @Operation(summary = "通过id删除商店")
    @Modifying
    @Transactional
    @Query(value = "delete from category where id = :id", nativeQuery = true)
    @RestResource(path = "deleteById")
    void deleteById(int id);

    @Operation(summary = "通过id更新商店")
    @Transactional
    @Modifying
    @Query("UPDATE Category s set s.categoryName = :categoryName where s.id = :id")
    @RestResource(path = "updateById")
    void updateById(int id,String categoryName);

    @Operation(summary = "添加商店")
    @Modifying
    @Transactional
    @Query(value="insert into category(category_name) values(:categoryName)", nativeQuery = true)
    @RestResource(path = "insert")
    void insert(String categoryName);

    @Operation(summary = "通过商店名查找商店")
    @Transactional
    @Query(value = "select * from category where category_name = :categoryName", nativeQuery = true)
    @RestResource(path = "findByCategoryName")
    Category findByCategoryName(String categoryName);

    @Operation(summary = "通过商店名判断商店是否存在")
    @RestResource(path = "existsByCategoryName")
    boolean existsByCategoryName(String categoryName);

    @Operation(summary = "将category_seq表中的id设置为0")
    @Modifying
    @Transactional
    @Query(value = "update category_seq set next_val = 1", nativeQuery = true)
    @RestResource(path = "setCategorySeqIdOne")
    void setCategorySeqIdOne();
}

package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "CategoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Operation(summary = "通过id查找分类")
    @Modifying
    @Transactional
    @Query(value = "select * from category where id = :id", nativeQuery = true)
    @RestResource(path = "findById")
    CategoryRepository findById(int id);

    @Operation(summary = "通过id删除分类")
    @Modifying
    @Transactional
    @Query(value = "delete from category where id = :id", nativeQuery = true)
    @RestResource(path = "deleteById")
    void deleteById(int id);

    @Operation(summary = "通过id更新分类")
    @Transactional
    @Modifying
    @Query("UPDATE Category s set s.categoryName = :categoryName where s.id = :id")
    @RestResource(path = "updateById")
    void updateById(int id,String categoryName);

}

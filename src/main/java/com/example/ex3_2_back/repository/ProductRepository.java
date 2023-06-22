package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.swing.text.html.Option;
import java.util.Optional;

@RepositoryRestResource(path = "ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Operation(summary = "通过产品名称查找")
    @RestResource(path = "findByName")
    Optional<Product> findByName(String name);

//    Optional<Product> findById(int id);
}

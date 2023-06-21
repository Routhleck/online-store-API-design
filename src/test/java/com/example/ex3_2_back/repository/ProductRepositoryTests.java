package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Product;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@Slf4j
public class ProductRepositoryTests {

    @Resource
    ProductRepository productRepository;

    @Test
    void findAll(){
        productRepository.findAll().forEach(System.out::println);
    }

    @Test
    void addProduct(){
        Product product = new Product();
        product.setName("delete");
        product.setDescription("待删除");
        product.setPrice(new BigDecimal("911"));
        product.setIsList(false);
        productRepository.save(product);
    }

    @Test
    void findLike(){
        productRepository.findAllByNameLike("%test%").forEach(System.out::println);
    }

    @Test
    void findByName(){
        productRepository.findByName("test").ifPresent(System.out::println);
    }

    @Test
    void deleteByName(){
        productRepository.deleteByName("delete");
    }

    @Test
    void deleteById(){
        productRepository.deleteById(52);
    }

    @Test
    void update(){
        System.out.println(productRepository.updateInfoByName("好",new BigDecimal("100.1"),"test"));
    }
}

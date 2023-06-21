package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    @Operation(summary = "serProductRepository", description = "serProductRepository")
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping("/showList")
    @Operation(summary = "展示商品列表", description = "展示商品列表")
    public Result showList(){
        return Result.success(productRepository.findAll());
    }

    @GetMapping("/search")
    @Operation(summary = "查询商品信息", description = "查询商品信息")
    public Result search(@RequestParam("name") String name){
        return Result.success(productRepository.findByName(name));
    }

    @GetMapping("/like")
    @Operation(summary = "按名字模糊查询", description = "按名字模糊查询")
    public Result like(@RequestParam("name") String name){
        return Result.success(productRepository.findAllByNameLike("%"+name+"%"));
    }

    @PostMapping("/new")
    @Operation(summary = "新建商品", description = "新建商品")
    public Result create(@RequestBody Product product){
        try {
            productRepository.save(product);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品", description = "删除商品")
    public Result delete(@RequestParam("name") String name){
        try {
            productRepository.deleteByName(name);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品信息", description = "更新商品信息")
    public Result update(@RequestParam("description") String description, @RequestParam("price") BigDecimal price, @RequestParam("name") String name){
        try {
            productRepository.updateInfoByName(description,price,name);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

}

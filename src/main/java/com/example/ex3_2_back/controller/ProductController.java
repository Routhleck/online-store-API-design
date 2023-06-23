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
        if (productRepository.count()==0){
            return Result.error("没有数据");
        }else {
            return Result.success(productRepository.findAll());
        }

    }

    @GetMapping("/search")
    @Operation(summary = "查询商品信息", description = "查询商品信息")
    public Result search(@RequestParam("name") String name){
        if (productRepository.existsByNameLike("%"+name+"%")){
            if (productRepository.existsByName(name)){
                return Result.success(productRepository.findByName(name));
            }else {
                return Result.success(productRepository.findAllByNameLike("%"+name+"%"));
            }
        }else {
            return Result.error("未找到该商品或相似商品");
        }
    }

    @PostMapping("/new")
    @Operation(summary = "新建商品", description = "新建商品")
    public Result create(@RequestBody Product product){
        try {
            productRepository.save(product);
            return Result.success();
        }catch (Exception e){
            return Result.error("商品"+"'"+product.getName()+"'"+"已存在");
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品", description = "删除商品")
    public Result delete(@RequestParam("name") String name){
        if(productRepository.existsByName(name)){
            productRepository.deleteByName(name);
            return Result.success();
        }else {
            return Result.error("商品"+"'"+name+"'"+"不存在，请重新输入");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品信息", description = "更新商品信息")
    public Result update(@RequestParam("description") String description, @RequestParam("price") BigDecimal price, @RequestParam("name") String name){
        if(productRepository.existsByName(name)){
            productRepository.updateInfoByName(description,price,name);
            return Result.success();
        }else {
            return Result.error("商品名不存在，请重试");
        }
    }

    @PutMapping("/listing")
    @Operation(summary = "商品上架和下架", description = "商品上架和下架")
    public Result listing(@RequestParam("isList") boolean isList, @RequestParam("name") String name){
        if(productRepository.existsByName(name)){
            productRepository.updateIsListByName(isList,name);
            return Result.success();
        }else {
            return Result.error("商品名不存在，请重试");
        }
    }
}

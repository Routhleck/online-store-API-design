package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Category;
import com.example.ex3_2_back.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    @Operation(summary = "setCategoryRepository", description = "setCategoryRepository")
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/showList")
    @Operation(summary = "展示分类列表", description = "展示分类列表")
    public Result showList(){
        if (categoryRepository.count()==0){
            return Result.error("没有数据");
        }else {
            return Result.success(categoryRepository.findAll());
        }

    }

    @GetMapping("/findById")
    @Operation(summary = "通过ID查询分类信息", description = "通过ID查询分类信息")
    public Result findById(@RequestParam("id") int id){
        if (categoryRepository.existsById(id)){
            return Result.success(categoryRepository.findById(id));
        }else {
            return Result.error("未找到该分类");
        }
    }

    @GetMapping("/findByCategoryName")
    @Operation(summary = "通过分类名查询商店信息", description = "通过分类名查询商店信息")
    public Result findByCategoryName(@RequestParam("categoryName") String categoryName){
        if (categoryRepository.existsByCategoryName(categoryName)){
            return Result.success(categoryRepository.findByCategoryName(categoryName));
        }else {
            return Result.error("未找到该分类");
        }
    }

    @DeleteMapping("/deleteById")
    @Operation(summary = "通过ID删除分类信息", description = "通过ID删除分类信息")
    public Result deleteById(@RequestParam("id") int id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return Result.success();
        }else {
            return Result.error("未找到该分类");
        }
    }

    @PutMapping("/updateById")
    @Operation(summary = "通过ID更新分类信息", description = "通过ID更新分类信息")
    public Result updateById(@RequestParam("id") int id,
                             @RequestParam("categoryName") String categoryName){
        if (categoryRepository.existsById(id)){
            categoryRepository.updateById(id,categoryName);
            return Result.success();
        }else {
            return Result.error("未找到该分类");
        }
    }

    @PostMapping("/insert")
    @Operation(summary = "插入分类信息", description = "插入分类信息")
    public Result insert(@RequestParam("categoryName") String categoryName){
        if (!categoryRepository.existsByCategoryName(categoryName)){
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
            return Result.success();
        }else {
            return Result.error("该分类已存在");
        }
    }

}

package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Shop;
import com.example.ex3_2_back.repository.ShopRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private ShopRepository shopRepository;

    @Autowired
    @Operation(summary = "setShopRepository", description = "setShopRepository")
    public void setShopRepository(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    @GetMapping("/showList")
    @Operation(summary = "展示商店列表", description = "展示商店列表")
    public Result showList(){
        if (shopRepository.count()==0){
            return Result.error("没有数据");
        }else {
            return Result.success(shopRepository.findAll());
        }

    }

    @GetMapping("/findById")
    @Operation(summary = "通过ID查询商店信息", description = "通过ID查询商店信息")
    public Result findById(@RequestParam("id") int id){
        if (shopRepository.existsById(id)){
            return Result.success(shopRepository.findById(id));
        }else {
            return Result.error("未找到该商店");
        }
    }

    @GetMapping("/findByShopName")
    @Operation(summary = "通过商店名查询商店信息", description = "通过商店名查询商店信息")
    public Result findByShopName(@RequestParam("shopName") String shopName){
        if (shopRepository.existsByShopName(shopName)){
            return Result.success(shopRepository.findByShopName(shopName));
        }else {
            return Result.error("未找到该商店");
        }
    }

    @DeleteMapping("/deleteById")
    @Operation(summary = "通过ID删除商店信息", description = "通过ID删除商店信息")
    public Result deleteById(@RequestParam("id") int id){
        if (shopRepository.existsById(id)){
            shopRepository.deleteById(id);
            return Result.success();
        }else {
            return Result.error("未找到该商店");
        }
    }

    @PutMapping("/updateById")
    @Operation(summary = "通过ID更新商店信息", description = "通过ID更新商店信息")
    public Result updateById(@RequestParam("id") int id,
                             @RequestParam("shopName") String shopName){
        if (shopRepository.existsById(id)){
            shopRepository.updateById(id,shopName);
            return Result.success();
        }else {
            return Result.error("未找到该商店");
        }
    }

    @PostMapping("/new")
    @Operation(summary = "插入商店信息", description = "插入商店信息")
    public Result insert(@RequestParam("shopName") String shopName){
        if (!shopRepository.existsByShopName(shopName)){
            Shop shop = new Shop();
            shop.setShopName(shopName);
            shopRepository.save(shop);
            return Result.success();
        }else {
            return Result.error("该商店已存在");
        }
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "删除所有商店信息", description = "删除所有商店信息")
    public Result deleteAll(){
        shopRepository.deleteAll();
        shopRepository.setShopSeqIdOne();
        return Result.success();
    }

}

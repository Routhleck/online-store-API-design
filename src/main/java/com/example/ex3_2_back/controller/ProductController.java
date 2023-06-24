package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Category;
import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.entity.Shop;
import com.example.ex3_2_back.repository.CategoryRepository;
import com.example.ex3_2_back.repository.ProductRepository;
import com.example.ex3_2_back.repository.ShopRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;

    private ShopRepository shopRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    @Operation(summary = "serProductRepository", description = "serProductRepository")
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Autowired
    @Operation(summary = "setShopRepository", description = "setShopRepository")
    public void setShopRepository(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    @Autowired
    @Operation(summary = "setCategoryRepository", description = "setCategoryRepository")
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
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

    @GetMapping("/findById")
    @Operation(summary = "通过id查询商品信息", description = "通过id查询商品信息")
    public Result findByIdResult(@RequestParam("id") int id){
        if (productRepository.existsById(id)){
            return Result.success(productRepository.findById(id));
        }else {
            return Result.error("未找到该商品");
        }
    }


    public Optional<Product> findById(@RequestParam("id") int id){
        if (productRepository.existsById(id)){
            return productRepository.findById(id);
        }
        else {
            return null;
        }
    }

    @GetMapping("/findByName")
    @Operation(summary = "通过商品名查询商品信息", description = "通过商品名查询商品信息")
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

    @GetMapping("/findByShopId")
    @Operation(summary = "通过商家id查询商品信息", description = "通过商家id查询商品信息")
    public Result findByShopId(@RequestParam("shop_id") int shop_id){
        if (shopRepository.existsById(shop_id)){
            return Result.success(productRepository.findAllByShopId(shop_id));
        }else {
            return Result.error("未找到该商家");
        }
    }

    @GetMapping("/findByCategoryId")
    @Operation(summary = "通过类别id查询商品信息", description = "通过类别id查询商品信息")
    public Result findByCategoryId(@RequestParam("category_id") int category_id){
        if (categoryRepository.existsById(category_id)){
            return Result.success(productRepository.findAllByCategoryId(category_id));
        }else {
            return Result.error("未找到该类别");
        }
    }

    @PostMapping("/new")
    @Operation(summary = "新建商品", description = "新建商品")
    public Result newProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("isList") boolean isList,
                             @RequestParam("shop_id") int shop_id,
                             @RequestParam("category_id") int category_id) {
        if (productRepository.existsByName(name)) {
            return Result.error("商品已存在，请重新输入");
        } else {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setIsList(isList);

            // 设置商家
            Optional<Shop> optionalShop = Optional.ofNullable(shopRepository.findById(shop_id));
            if (optionalShop.isEmpty()) {
                return Result.error("商家不存在，请输入有效的商家ID");
            }
            Shop shop = optionalShop.get();
            product.setShop(shop);

            // 设置分类
            Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.findById(category_id));
            if (optionalCategory.isEmpty()) {
                return Result.error("分类不存在，请输入有效的分类ID");
            }
            Category category = optionalCategory.get();
            product.setCategory(category);

            productRepository.save(product);
            return Result.success();
        }
    }


    @DeleteMapping("/deleteById")
    @Operation(summary = "根据ID删除商品", description = "根据ID删除商品")
    public Result deleteById(@RequestParam("id") int id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return Result.success();
        }else {
            return Result.error("商品ID不存在，请重新输入");
        }
    }

    @DeleteMapping("/deleteByName")
    @Operation(summary = "根据商品名删除商品", description = "根据商品名删除商品")
    public Result deleteByName(@RequestParam("name") String name){
        if(productRepository.existsByName(name)){
            productRepository.deleteByName(name);
            return Result.success();
        }else {
            return Result.error("商品"+"'"+name+"'"+"不存在，请重新输入");
        }
    }

    @PutMapping("/updateById")
    @Operation(summary = "根据ID更新商品信息", description = "根据ID更新商品信息")
    public Result updateById(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("shop_id") int shop_id,
                             @RequestParam("category_id") int category_id){

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            // 若有参数为空，则不修改
            if(name!=null){
                product.setName(name);
            }
            if(description!=null){
                product.setDescription(description);
            }
            if(price!=null){
                product.setPrice(price);
            }
            if(shop_id!=0){
                // 设置商家
                Optional<Shop> optionalShop = Optional.ofNullable(shopRepository.findById(shop_id));
                if (optionalShop.isEmpty()) {
                    return Result.error("商家不存在，请输入有效的商家ID");
                }
                Shop shop = optionalShop.get();
                product.setShop(shop);
            }

            if(category_id!=0) {
                // 设置分类
                Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.findById(category_id));
                if (optionalCategory.isEmpty()) {
                    return Result.error("分类不存在，请输入有效的分类ID");
                }
            }
            productRepository.save(product);
            return Result.success();
        }else {
            return Result.error("商品ID不存在，请重新输入");
        }
    }

    @PutMapping("/listTrue")
    @Operation(summary = "上架商品", description = "上架商品")
    public Result listTrue(@RequestParam("id") int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setIsList(true);
            productRepository.save(product);
            return Result.success();
        }else {
            return Result.error("商品ID不存在，请重新输入");
        }
    }

    @PutMapping("/listFalse")
    @Operation(summary = "下架商品", description = "下架商品")
    public Result listFalse(@RequestParam("id") int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setIsList(false);
            productRepository.save(product);
            return Result.success();
        }else {
            return Result.error("商品ID不存在，请重新输入");
        }
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "删除所有商品", description = "删除所有商品")
    public Result deleteAll(){
        productRepository.deleteAll();
        productRepository.resetId();
        return Result.success();
    }

    @GetMapping("/findByProductId")
    @Operation(summary = "通过商品id查询商品信息", description = "通过商品id查询商品信息")
    public Optional<Product> findByProductId(int productId) {
        if (productRepository.existsById(productId)) {
            return productRepository.findById(productId);
        } else {
            return Optional.empty();
        }
    }
}

package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.ProductDetail;
import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.CartItemRepository;
import com.example.ex3_2_back.repository.CategoryRepository;
import com.example.ex3_2_back.repository.ShopRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
    private CartItemRepository cartItemRepository;
    private CartController cartRepository;

    private ShopRepository shopRepository;

    private CategoryRepository categoryRepository;

    private ProductController productRepository;

    @Autowired
    @Operation(summary = "serCartItemRepository", description = "serCartItemRepository")
    public void setProductRepository(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }

    @Autowired
    @Operation(summary = "serCartRepository", description = "serCartRepository")
    public void setCartRepository(CartController cartRepository){
        this.cartRepository = cartRepository;
    }

    @Autowired
    @Operation(summary = "serProductRepository", description = "serProductRepository")
    public void setProductRepository(ProductController productRepository){
        this.productRepository = productRepository;
    }

    @Autowired
    @Operation(summary = "serShopRepository", description = "serShopRepository")
    public void setShopRepository(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    @Autowired
    @Operation(summary = "serCategoryRepository", description = "serCategoryRepository")
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/findByUserId")
    @Operation(summary = "通过用户ID展示购物车商品列表",description = "通过用户ID展示购物车商品列表")
    public Result findByUserId(@RequestParam("user_id") int userId){
        Cart cart = cartRepository.findByUserId(userId);
        // 判断是否为空
        if(cart == null){
            return Result.error("用户对应购物车不存在");
        }
        List<Map<String, Object>> cartItems = cartItemRepository.findByCartId(cart.getId());

        // 判断是否为空
        if(cartItems.isEmpty()){
            return Result.error("购物车为空");
        }
        return Result.success(cartItems);
    }

    @GetMapping("/findDetailsByUserId")
    @Operation(summary = "通过用户ID展示购物车商品列表",description = "通过用户ID展示购物车商品列表")
    public Result findDetailsByUserId(@RequestParam("user_id") int userId){

        Cart cart = cartRepository.findByUserId(userId);

        // 判断是否为空
        if(cart == null){
            return Result.error("用户对应购物车不存在");
        }

        List<ProductDetail> cartItems = cartItemRepository.findDetailsByUserId(userId);
        // 判断是否为空
        if(cartItems.isEmpty()){
            return Result.error("购物车为空");
        }
        return Result.success(cartItems);
    }

    @GetMapping("/findDetailsByUserIdShopIdCategoryId")
    @Operation(summary = "通过用户ID、商店ID、商品类别ID展示购物车商品列表",description = "通过用户ID、商店ID、商品类别ID展示购物车商品列表")
    public Result findDetailsByUserIdShopIdCategoryId(@RequestParam("user_id") int userId,
                                                @RequestParam("shop_id") int shopId,
                                                @RequestParam("category_id") int categoryId){
        Cart cart = cartRepository.findByUserId(userId);

        // 判断是否为空
        if(cart == null){
            return Result.error("用户对应购物车不存在");
        }

        Shop shop = shopRepository.findById(shopId);

        // 判断是否为空
        if(shop == null){
            return Result.error("商家不存在");
        }

        Category category = categoryRepository.findById(categoryId);

        // 判断是否为空
        if(category == null){
            return Result.error("商品类别不存在");
        }

        List<ProductDetail> cartItems = cartItemRepository.findDetailsByUserIdShopIdCategoryId(userId,shopId,categoryId);

        // 判断是否为空
        if(cartItems.isEmpty()){
            return Result.error("购物车为空");
        }
        return Result.success(cartItems);
    }

    @GetMapping("/findByUserIdShopIdCategoryId")
    @Operation(summary = "通过用户ID、商店ID、商品类别ID展示购物车商品列表",description = "通过用户ID、商店ID、商品类别ID展示购物车商品列表")
    public Result findByUserIdShopIdCategoryId(@RequestParam("user_id") int userId,
                                                @RequestParam("shop_id") int shopId,
                                                @RequestParam("category_id") int categoryId){
        Cart cart = cartRepository.findByUserId(userId);

        // 判断是否为空
        if(cart == null){
            return Result.error("购物车为空");
        }

        Shop shop = shopRepository.findById(shopId);

        // 判断是否为空
        if(shop == null){
            return Result.error("商家不存在");
        }

        Category category = categoryRepository.findById(categoryId);

        // 判断是否为空
        if(category == null){
            return Result.error("商品类别不存在");
        }

        List<Map<String, Object>> cartItems = cartItemRepository.findByCartIdShopIdCategoryId(cart.getId(),shop.getId(),category.getId());

        // 判断是否为空
        if(cartItems.isEmpty()){
            return Result.error("购物车为空");
        }
        return Result.success(cartItems);
    }

//    @GetMapping("/findById")
//    @Operation(summary = "展示购物车商品列表",description = "展示购物车商品列表")
//    public Result findById(@RequestParam("cartId") int cartId){
//        return Result.success(cartItemRepository.findByCartId(cartId));
//    }

//    @GetMapping("/findOne")
//    @Operation(summary = "根据购物车id和商品id查看信息",description = "根据购物车id和商品id查看信息")
//    public Result findOne(@RequestParam("cartId") int cartId,
//                          @RequestParam("productId") int productId){
//        return Result.success(cartItemRepository.findByCartIdAndProductId(cartId,productId));
//    }


    @PostMapping("/insertByUserIdProductIdQuantity")
    @Operation(summary = "通过用户ID, 商品ID和数量向购物车内添加商品",description = "通过用户ID, 商品ID和数量向购物车内添加商品")
    public Result insertByUserIdProductIdQuantity(@RequestParam("user_id") int userId,
                                                  @RequestParam("product_id") int productId,
                                                  @RequestParam("quantity") int quantity){
        try{
            // 通过用户ID查找Cart
            Cart cart = cartRepository.findByUserId(userId);

            // 判断是否为空
            if(cart == null){
                return Result.error("购物车不存在");
            }

            // 通过商品ID查找Product
            Optional<Product> product = productRepository.findByProductId(productId);

            // 判断是否为空
            if(product.isEmpty()){
                return Result.error("商品不存在");
            }

            // 向购物车内添加商品
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product.get());
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/deleteByUserIdProductId")
    @Operation(summary = "通过用户ID和商品ID删除购物车内的商品",description = "通过用户ID和商品ID删除购物车内的商品")
    public Result deleteByUserIdProductId(@RequestParam("user_id") int userId,
                                @RequestParam("product_id") int productId){
        try{
            Cart cart = cartRepository.findByUserId(userId);

            // 判断是否为空
            if(cart == null){
                return Result.error("购物车不存在");
            }

            Optional<Product> product = productRepository.findByIdRequset(productId);

            // 判断是否为空

            if(product.isEmpty()){
                return Result.error("商品不存在");
            }

            cartItemRepository.deleteByIdProductId(cart.getId(),product.get().getId());

            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/deleteByUserIdShopId")
    @Operation(summary = "通过用户ID和商店ID删除购物车内的商品",description = "通过用户ID和商店ID删除购物车内的商品")
    public Result deleteByUserIdShopId(@RequestParam("user_id") int userId,
                                @RequestParam("shop_id") int shopId){
        try{
            Cart cart = cartRepository.findByUserId(userId);

            // 判断是否为空
            if(cart == null){
                return Result.error("购物车不存在");
            }

            Optional<Shop> shop = Optional.ofNullable(shopRepository.findById(shopId));

            // 判断是否为空

            if(shop.isEmpty()){
                return Result.error("商店不存在");
            }

            cartItemRepository.deleteByIdShopId(cart.getId(),shop.get().getId());

            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/deleteByUserIdCategoryId")
    @Operation(summary = "通过用户ID和商品类别ID删除购物车内的商品",description = "通过用户ID和商品类别ID删除购物车内的商品")
    public Result deleteByUserIdCategoryId(@RequestParam("user_id") int userId,
                                @RequestParam("category_id") int categoryId){
        try{
            Cart cart = cartRepository.findByUserId(userId);

            // 判断是否为空
            if(cart == null){
                return Result.error("购物车不存在");
            }

            Optional<Category> category = Optional.ofNullable(categoryRepository.findById(categoryId));

            // 判断是否为空

            if(category.isEmpty()){
                return Result.error("商品类别不存在");
            }

            cartItemRepository.deleteByIdCategoryId(cart.getId(),category.get().getId());

            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PutMapping("/updateQuantity")
    @Operation(summary = "更新购物车内商品数量",description = "更新购物车内商品数量")
    public Result updateProduct(@RequestParam("user_id") int userId,
                                @RequestParam("product_id") int productId,
                                @RequestParam("quantity") int quantity){
        try{
            Cart cart = cartRepository.findByUserId(userId);

            // 判断是否为空
            if(cart == null){
                return Result.error("购物车不存在");
            }

            cartItemRepository.updateByIdProductIdQuantity(cart.getId(),productId,quantity);
            return Result.success();
        }catch (Exception e){
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

}

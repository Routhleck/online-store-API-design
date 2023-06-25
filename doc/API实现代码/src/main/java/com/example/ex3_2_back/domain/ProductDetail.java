package com.example.ex3_2_back.domain;

import com.example.ex3_2_back.entity.Product;

/**
 * @program: online-store-API-design
 * @description: 商品Detail
 * @author: 20301037_Routhleck
 * @create: 2023-06-25 21:25
 **/
public class ProductDetail {

    private Product product;
    private Integer quantity;

    public ProductDetail(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // getter and setter

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

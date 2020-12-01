package com.prayerlaputa.controller;

import com.prayerlaputa.model.Product;
import com.prayerlaputa.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author chenglong.yu
 * created on 2020/12/1
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;


    /**
     * Get product by id
     *
     * @param productId
     * @return
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long productId) {
        return productService.select(productId);
    }

    /**
     * Update product by id
     *
     * @param id
     * @return
     */

    @GetMapping("/update")
    public Product updateProduct(Long id, String name, Double price) throws IOException {
        Product newProduct = new Product();
        newProduct.setId(id);
        newProduct.setPrice(price);
        newProduct.setName(name);
        return productService.update(id, newProduct);
    }

    /**
     * Delete product by id
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public boolean deleteProduct(Long id) throws IOException {
        return productService.delete(id);
    }

    /**
     * 为了方便测试，这里使用GET请求（按道理应该用POST）。
     *
     * @return
     */
    @GetMapping("/add")
    public boolean addProduct(String name, Double price) throws IOException {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        return productService.add(newProduct);
    }
}

package com.prayerlaputa.service;

import com.prayerlaputa.datasource.ReadOnly;
import com.prayerlaputa.mapper.ProductDAO;
import com.prayerlaputa.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 *
 */
@Service
public class ProductService {

    @Autowired
    private ProductDAO productDao;

    @ReadOnly
    public Product select(long productId) {
        return productDao.select(productId);
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public Product update(long productId, Product newProduct) throws IOException {

        if (productDao.update(newProduct) <= 0) {
            throw new IOException("Update product:" + productId + "failed");
        }
        return newProduct;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(Product newProduct) throws IOException {
        Integer num = productDao.insert(newProduct);
        if (num <= 0) {
            throw new IOException("Add product failed");
        }
        return true;
    }


    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(long productId) throws IOException {
        Integer num = productDao.delete(productId);
        if (num <= 0) {
            throw new IOException("Delete product:" + productId + "failed");
        }
        return true;
    }

}

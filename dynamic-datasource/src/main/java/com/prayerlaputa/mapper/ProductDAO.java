package com.prayerlaputa.mapper;

import com.prayerlaputa.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenglong.yu
 * created on 2020/12/1
 */
@Mapper
public interface ProductDAO {

    Product select(@Param("id") long id);

    Integer update(Product product);

    Integer insert(Product product);

    Integer delete(long productId);
}

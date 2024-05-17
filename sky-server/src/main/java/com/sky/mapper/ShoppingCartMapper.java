package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import com.sky.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumber(ShoppingCart shoppingCart);

    void insert(List<ShoppingCart> shoppingCartList);

    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteAll(ShoppingCart shoppingCart);

    /**
     * 批量插入购物车数据
     *
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);
}

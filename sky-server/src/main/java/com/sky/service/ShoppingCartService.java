package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.entity.User;

import java.util.List;

/**
 * TODO
 *
 * @author LHF
 * @version 1.0
 * @date 2024/5/14 18:58
 */
public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param: shoppingCartDTO
     * @return: void
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @param:
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    List<ShoppingCart> list();

    /**
     * 清空购物车
     * @param:
     * @return: void
     */
    void deleteAll();
}
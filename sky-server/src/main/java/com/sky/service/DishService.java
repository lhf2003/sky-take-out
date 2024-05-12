package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /***
     * @description: 新增菜品
     * @param: dishDTO
     * @return: void
     */
    void insert(DishDTO dishDTO);

    /***
     * @description: 分页查询菜品
     * @param: dishPageQueryDTO
     * @return: com.sky.result.PageResult
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品（一个或多个）
     *
     * @param: ids
     * @return: void
     */
    void delete(List<Long> ids);

    /**
     * 根据菜品id查询对应的口味记录
     *
     * @param: id
     * @return: com.sky.vo.DishVO
     */
    DishVO getDishById(Long id);

    /**
     * 修改菜品以及对应的口味
     *
     * @param: dishDTO
     * @return: void
     */
    void updateDishWithFlavor(DishDTO dishDTO);

    /**
     * 根据分类id查询该分类菜品
     * @param: id
     * @return: java.util.List<com.sky.entity.Dish>
     */
    List<Dish> list(Long categoryId);
}

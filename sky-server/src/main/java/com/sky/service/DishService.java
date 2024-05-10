package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

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

    /***
     * @description: 删除菜品（一个或多个）
     * @param: ids
     * @return: void
     */
    void delete(List<Long> ids);
}

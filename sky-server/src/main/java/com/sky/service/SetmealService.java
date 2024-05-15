package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;

import java.util.List;


public interface SetmealService {
    /**
     * 新增套餐
     *
     * @param: setmealDTO
     * @return: void
     */
    void insert(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void statusModify(Integer status, Long id);

    void deleteSetMeal(List<Long> ids);

    Setmeal getById(Long id);

    void update(SetmealDTO setmealDTO);

    /**
     * 根据分类id查询套餐
     *
     * @param: categotyId
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询包含的菜品
     *
     * @param: id
     * @return: java.util.List<com.sky.vo.DishItemVO>
     */
    List<DishItemVO> getDishItemBySetmealId(Long id);
}
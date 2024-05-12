package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;

import java.util.List;

/**
 * TODO
 *
 * @author LHF
 * @version 1.0
 * @date 2024/5/11 18:21
 */
public interface SetmealService {
    /**
     * 新增套餐
     *
     * @param: setmealDTO
     * @return: void
     */
    void insert(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void statusModify(Integer status,Long id);

    void deleteSetMeal(List<Long> ids);

    Setmeal getById(Long id);

    void update(SetmealDTO setmealDTO);
}
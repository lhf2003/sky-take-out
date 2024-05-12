package com.sky.mapper;


import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> hasConnect(List<Long> ids);


    /**
     * 新增套餐的菜品
     * @param: setmealDishes
     * @return: void
     */
    void insert(List<SetmealDish> setmealDishes);

    @Delete("delete from setmeal_dish where setmeal_id = #{setMealId}")
    void delete(Long setMealId);
}

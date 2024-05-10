package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    @Select("select * from dish_flavor")
    List<DishFlavor> getFlavers();

    /***
     * @description: 菜品新增的口味
     * @param: flavors
     * @return: void
     */
    void insert(List<DishFlavor> flavors);

    /***
     * @description: 根据菜品id删除口味记录
     * @param: id
     * @return: void
     */
    void deleteByDishId(List<Long> dishId);
}

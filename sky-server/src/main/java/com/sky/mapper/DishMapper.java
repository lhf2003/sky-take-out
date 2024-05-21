package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /***
     * @description: 菜品分页查询
     * @param: dishPageQueryDTO
     * @return: com.github.pagehelper.Page<com.sky.entity.Dish>
     */
    Page<Dish> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /***
     * @description: 根据id查询菜品记录
     * @param: id
     * @return: com.sky.entity.Dish
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /***
     * @description: 根据id删除菜品记录
     * @param: ids
     * @return: void
     */
    void deleteById(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据分类id查询该分类菜品
     *
     * @param: categoryId
     * @return: java.util.List<com.sky.entity.Dish>
     */
    List<Dish> list(Dish dish);

    /**
     * @param status
     * @return java.lang.Integer
     * @date 2024/5/21 15:12
     */
    @Select("select count(id) from dish where status = #{status}")
    Integer countByStatus(Integer status);
}

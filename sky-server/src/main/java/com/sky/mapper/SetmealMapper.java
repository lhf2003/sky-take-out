package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增套餐
     * @param: setmealDTO
     * @return: void
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setMeal);

    /**
     * 分页查询
     * @param: setmealPageQueryDTO
     * @return: com.github.pagehelper.Page<com.sky.entity.Setmeal>
     */
    Page<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setMeal);

    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    @Delete("delete from setmeal where id = #{id}")
    void delete(Long id);

    /**
     * 根据分类id查询套餐
     * @param: categotyId
     * @return: java.util.List<com.sky.entity.Setmeal>
     */

    List<Setmeal> list(Setmeal setmeal);
}

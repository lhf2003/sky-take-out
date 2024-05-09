package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /***
     * @description: 新增分类
     * @param: categoryDTO
     * @return: void
     */
    void addCategory(CategoryDTO categoryDTO);

    /***
     * @description: 分页查询
     * @param: categoryPageQueryDTO
     * @return: com.sky.result.PageResult
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /***
     * @description: 编辑分类信息
     * @param: categoryDTO
     * @return: void
     */
    void updateCategory(CategoryDTO categoryDTO);

    /***
     * @description: 启用或禁用分类
     * @param: status id
     * @return: void
     */
    void statusModify(Integer status, Long id);

    /***
     * @description: 根据id删除分类
     * @param: id
     * @return: void
     */
    void deleteById(Long id);

    List<Category> list(Integer type);
}

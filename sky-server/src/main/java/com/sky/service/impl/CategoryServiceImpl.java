package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @description: TODO
 * @date 2024/5/8 16:37
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /***
     * @description: 新增分类
     * @param: categoryDTO
     * @return: void
     */
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.ENABLE);
        //TODO 排序默认？？?

//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.addCategory(category);
    }

    /***
     * @description: 分类分页查询，包括根据name和type进行模糊查询
     * @param: categoryPageQueryDTO
     * @return: com.sky.result.PageResult
     */
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal(); //分页查询得到的记录的总数
        List records = page.getResult(); //分页查询的记录数据集
        return new PageResult(total, records);
    }

    /***
     * @description: 编辑分类信息
     * @param: categoryDTO
     * @return: void
     */
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    /***
     * @description: 启用或禁用分类
     * @param: status id
     * @return: void
     */
    public void statusModify(Integer status, Long id) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);

//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);

    }

    /***
     * @description: 根据id删除分类
     * @param: id
     * @return: void
     */
    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品
        Integer count = dishMapper.countByCategoryId(id);
        //如果关联了菜品就不能执行删除操作，抛出异常
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联了套餐
        count = setmealMapper.countByCategoryId(id);
        //如果关联了套餐就不能执行删除操作，抛出异常
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //不满足以上条件再执行删除操作
        categoryMapper.deleteById(id);
    }

    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
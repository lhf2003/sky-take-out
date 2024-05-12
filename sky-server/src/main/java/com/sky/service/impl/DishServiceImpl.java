package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @description: TODO
 * @date 2024/5/9 21:59
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setMealDishMapper;

    /**
     * 新增菜品
     *
     * @param: dishDTO
     * @return: void
     */
    @Transactional
    public void insert(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //向dish表插入数据
        dishMapper.insert(dish);

        //向dish_flavor表插入数据
        Long dishId = dish.getId(); //获取菜品id，以便与菜品口味表关联
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insert(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param: dishPageQueryDTO
     * @return: com.sky.result.PageResult
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除菜品记录（一或多个）
     *
     * @param: ids
     * @return: void
     */
    @Transactional
    public void delete(List<Long> ids) {
        //删除前确认菜品为停售状态（0），起售状态（1）无法删除
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //确认菜品是否关联了套餐
        List<Long> connect = setMealDishMapper.hasConnect(ids);
        if (connect != null && !connect.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除菜品记录
        dishMapper.deleteById(ids);
        //删除菜品对应的口味记录
        dishFlavorMapper.deleteByDishId(ids);
    }

    /**
     * 根据菜品id查询对应的口味记录
     *
     * @param: id
     * @return: com.sky.vo.DishVO
     */
    public DishVO getDishById(Long id) {
        //查询出菜品记录
        Dish dish = dishMapper.getById(id);
        //查询菜品记录对应的口味记录
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getByDishId(id);

        //创建DishVO对象并将查询到的两条记录复制到其中返回给前端
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    /**
     * 修改菜品以及对应的口味
     *
     * @param: dishDTO
     * @return: void
     */
    public void updateDishWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //修改菜品信息
        dishMapper.update(dish);
        //删除口味记录
        dishFlavorMapper.deleteByDishId(Collections.singletonList(dishDTO.getId()));
        //添加新的口味记录
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insert(flavors);
        }
    }

    /**
     * 根据分类id查询该分类菜品
     *
     * @param: id
     * @return: java.util.List<com.sky.entity.Dish>
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }
}
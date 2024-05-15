package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setMealMapper;
    @Autowired
    private SetmealDishMapper setMealDishMapper;

    /**
     * 新增套餐
     *
     * @param: setmealDTO
     * @return: void
     */
    public void insert(SetmealDTO setmealDTO) {
        Setmeal setMeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setMeal);

        setMeal.setStatus(StatusConstant.ENABLE);
        setMealMapper.insert(setMeal);

        //将菜品与套餐关联起来，并存储到setmeal_dish表
        Long setMealId = setMeal.getId(); //获取当前套餐的id
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setMealId); //将每个套餐中的setMealDish与setMealId关联
        });
        setMealDishMapper.insert(setmealDishes);
    }

    /**
     * 分页查询
     *
     * @param: setmealPageQueryDTO
     * @return: com.sky.result.PageResult
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> setmealPage = setMealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }

    public void statusModify(Integer status, Long id) {
        Setmeal setMeal = new Setmeal();
        setMeal.setStatus(status);
        setMeal.setId(id);
        setMealMapper.update(setMeal);
    }

    public void deleteSetMeal(List<Long> ids) {
        //起售的套餐不能删除
        for (Long id : ids) {
            Setmeal setMeal = setMealMapper.getById(id);
            Integer status = setMeal.getStatus();
            if (status == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        for (Long id : ids) {
            setMealMapper.delete(id);//删除套餐表中的数据
            setMealDishMapper.delete(id);//删除套餐菜品关系表中的数据
        }
    }

    public Setmeal getById(Long id) {
        return setMealMapper.getById(id);
    }

    public void update(SetmealDTO setmealDTO) {
        Setmeal setMeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setMeal);
        setMealMapper.update(setMeal);
    }

    /**
     * 根据分类id查询套餐
     *
     * @param: categotyId
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    public List<Setmeal> list(Setmeal setmeal) {
        return setMealMapper.list(setmeal);
    }

    /**
     * 根据套餐id查询包含的菜品
     *
     * @param: setmealId
     * @return: java.util.List<com.sky.vo.DishItemVO>
     */
    public List<DishItemVO> getDishItemBySetmealId(Long setmealId) {
        return setMealDishMapper.getDishItemBySetmealId(setmealId);
    }
}
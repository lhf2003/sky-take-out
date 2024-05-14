package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author LHF
 * @version 1.0
 * @date 2024/5/13 22:42
 */
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐浏览接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result<List<Setmeal>> getSetmealByCategoryId(Long categotyId){
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categotyId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> setmealList = setmealService.list(setmeal);
        return Result.success(setmealList);
    }
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> getDishInSetmealById(@PathVariable Long id){
        List<DishItemVO> dishes = setmealService.getDishItemBySetmealId(id);
        return Result.success(dishes);
    }
}
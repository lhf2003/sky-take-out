package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @description: TODO
 * @date 2024/5/9 21:57
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    @Transactional //开启事务
    public Result saveDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.insert(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.delete(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品记录")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("根据id查询菜品记录：{}", id);
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.updateDishWithFlavor(dishDTO);
        return Result.success();
    }
}
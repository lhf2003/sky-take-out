package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @date 2024/5/13 22:25
 */

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> getDishByCategoryId(Long categoryId) {
        //构造redis中的key
        String key = "dish_" + categoryId;

        //查询redis是否有该key
        List<DishVO> dishList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (dishList != null && !dishList.isEmpty()) {
            //redis中存在该key直接返回数据
            return Result.success(dishList);
        }

        //redis中不存在该key，查询数据库并将返回的数据存入redis中
        dishList = dishService.listWithFlavor(categoryId);
        redisTemplate.opsForValue().set(key,dishList);
        return Result.success(dishList);

    }
}
package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @description: TODO
 * @date 2024/5/8 16:36
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /***
     * @description: 新增分类
     * @param: categoryDTO
     * @return: com.sky.result.Result
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}",categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /*** 
     * @description: 分类分页查询
     * @param: categoryPageQueryDTO 
     * @return: com.sky.result.Result
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询参数：{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /***
     * @description: 修改分类
     * @param: categoryDTO
     * @return: com.sky.result.Result
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}",categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /***
     * @description: 启用或禁用分类
     * @param: status id
     * @return: com.sky.result.Result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用分类")
    public Result statusModify(@PathVariable Integer status,Long id){
        log.info("启用/禁用分类");
        categoryService.statusModify(status,id);
        return Result.success();
    }

    /***
     * @description: 根据id删除分类
     * @param: id
     * @return: com.sky.result.Result
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteById(Long id){
        log.info("根据id删除分类；{}",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
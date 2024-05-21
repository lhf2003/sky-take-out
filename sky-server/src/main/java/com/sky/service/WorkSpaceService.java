package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {
    /**
     * @param begin
     * @param end
     * @return com.sky.vo.BusinessDataVO
     * @date 2024/5/21 15:02
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询菜品总览
     *
     * @param: begin end
     * @return: com.sky.vo.BusinessDataVO
     */
    DishOverViewVO getOverviewDishes();

    /**
     * 查询套餐总览
     *
     * @return com.sky.vo.SetmealOverViewVO
     * @date 2024/5/21 15:18
     */
    SetmealOverViewVO getOverviewSetmeals();

    /**
     * 查询订单管理数据
     *
     * @return com.sky.vo.OrderOverViewVO
     * @date 2024/5/21 15:48
     */
    OrderOverViewVO getOverviewOrders();

}

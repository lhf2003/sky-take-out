package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 查询今日运营数据
     *
     * @param:
     * @return: com.sky.vo.BusinessDataVO
     */
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //新增用户
        Integer newUsers = userMapper.countByMap(map);
        //当天的全部订单数
        Integer totalOrder = orderMapper.countByMap(map);

        //营业额
        map.put("status", 5);
        Double turnover = orderMapper.sumByMap(map);
        //当天的有效订单数
        Integer validOrder = orderMapper.countByMap(map);
        //订单完成率
        Double rate = 0.0;
        if (totalOrder != 0) {
            rate = validOrder.doubleValue() / totalOrder;
        }
        //平均客单价
        Double unitPrice = 0.0;
        if (validOrder != 0.0) {
            unitPrice = turnover / validOrder;
        }
        return BusinessDataVO
                .builder()
                .newUsers(newUsers)
                .orderCompletionRate(rate)
                .validOrderCount(validOrder)
                .unitPrice(unitPrice)
                .turnover(turnover)
                .build();
    }

    /**
     * 查询菜品总览
     *
     * @param: begin end
     * @return: com.sky.vo.BusinessDataVO
     */
    public DishOverViewVO getOverviewDishes() {
        //已启售总数量
        Integer opened = dishMapper.countByStatus(StatusConstant.ENABLE);
        //已停售总数量
        Integer stopped = dishMapper.countByStatus(StatusConstant.DISABLE);
        return DishOverViewVO
                .builder()
                .discontinued(stopped)
                .sold(opened)
                .build();
    }

    /**
     * 查询套餐总览
     *
     * @return com.sky.vo.SetmealOverViewVO
     * @date 2024/5/21 15:18
     */
    public SetmealOverViewVO getOverviewSetmeals() {
        //已启售总数量
        Integer opened = setmealMapper.countByStatus(StatusConstant.ENABLE);
        //已停售总数量
        Integer stopped = setmealMapper.countByStatus(StatusConstant.DISABLE);
        return SetmealOverViewVO
                .builder()
                .discontinued(stopped)
                .sold(opened)
                .build();
    }

    /**
     * 查询订单管理数据
     *
     * @return com.sky.vo.OrderOverViewVO
     * @date 2024/5/21 15:48
     */
    public OrderOverViewVO getOverviewOrders() {
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //全部订单
        Integer allOrders = orderMapper.countByMap(map);
        //待接单数量
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.countByMap(map);
        //待派送数量
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);
        //已完成数量
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);
        //已取消数量
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        return OrderOverViewVO
                .builder()
                .allOrders(allOrders)
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .build();
    }
}
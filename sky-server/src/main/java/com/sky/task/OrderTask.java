package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @date 2024/5/17 19:24
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时未支付订单
     *
     * @param:
     * @return: void
     */
    @Scheduled(cron = "0 * * * * ?") //每秒钟执行一次
    public void processTimeOutOrder() {
        log.info("定时处理超时未支付订单：{}", LocalDateTime.now());
        //超时时间
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        //查询订单状态为待付款且超时15分钟的订单
        List<Orders> ordersList = orderMapper.getStatusAndTimeOut(Orders.PENDING_PAYMENT, time);

        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders orders : ordersList) {
                //将该订单状态设置为已取消
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                //更新数据库中对应的记录
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理一直处于派送中的订单
     * @param:
     * @return: void
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天一点执行一次
    public void processDeliveryOrder(){
        log.info("处理一直处于派送中的订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        //查询处于派送中的订单
        List<Orders> ordersList = orderMapper.getStatusAndTimeOut(Orders.DELIVERY_IN_PROGRESS, time);

        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders orders : ordersList) {
                //将该订单状态设置为已完成
                orders.setStatus(Orders.COMPLETED);
                //更新数据库中对应的记录
                orderMapper.update(orders);
            }
        }
    }
}
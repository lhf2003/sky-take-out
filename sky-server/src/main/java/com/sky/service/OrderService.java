package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {
    /**
     * 用户下单
     * @param: ordersSubmitDTO
     * @return: java.util.List<com.sky.dto.OrdersSubmitDTO>
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.*;

public interface OrderService {
    /**
     * 用户下单
     *
     * @param: ordersSubmitDTO
     * @return: java.util.List<com.sky.dto.OrdersSubmitDTO>
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 用户端订单分页查询
     *
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult pageQuery4User(int page, int pageSize, Integer status);

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * 用户取消订单
     *
     * @param: id
     * @return: void
     */
    void userCancelById(Long id) throws Exception;

    /**
     * 再来一单
     *
     * @param id
     */
    void repetition(Long id);

    /**
     * 条件搜索订单
     *
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    OrderStatisticsVO statistics();

    /**
     * 接单
     *
     * @param: ordersConfirmDTO
     * @return: void
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param: ordersConfirmDTO
     * @return: void
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 取消订单
     *
     * @param: ordersCancelDTO
     * @return: void
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 派送订单
     *
     * @param: id
     * @return: void
     */
    void delivery(Long id);

    /**
     * 完成订单
     *
     * @param: id
     * @return: void
     */
    void complete(Long id);

    /**
     * 客户催单
     *
     * @param: id
     * @return: void
     */
    void reminder(Long id);


}
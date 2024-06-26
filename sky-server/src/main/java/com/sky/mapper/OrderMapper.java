package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     *
     * @param order
     */
    void insert(Orders order);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 用于替换微信支付更新数据库状态的问题
     *
     * @param orderStatus
     * @param orderPaidStatus
     */
    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);

    /**
     * 分页条件查询并按下单时间排序
     *
     * @param ordersPageQueryDTO
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     *
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 根据状态统计订单数量
     *
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 处理超时未付款订单
     *
     * @param: cancelled time
     * @return: java.util.List<com.sky.entity.Orders>
     */
    @Select("select * from orders where status = #{status} and order_time < #{time}")
    List<Orders> getStatusAndTimeOut(Integer status, LocalDateTime time);

    /**
     * 营业额统计
     *
     * @param: map
     * @return: java.lang.Double
     */
    Double sumByMap(Map<String, Object> map);

    /**
     * 订单统计
     *
     * @param: begin end status
     * @return: java.lang.Integer
     */
    Integer countByMap(Map<String, Object> map);

    /**
     * 根据指定时间区间查询销量Top10
     *
     * @param: begin，end
     * @return: java.util.List<com.sky.dto.GoodsSalesDTO>
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}

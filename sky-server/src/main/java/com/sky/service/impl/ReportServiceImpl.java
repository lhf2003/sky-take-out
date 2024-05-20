package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 营业额统计
     *
     * @param: begin;end
     * @return: com.sky.vo.TurnoverReportVO
     */
    public TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end) {
        //得到从begin到end的日期集合
        List<LocalDate> dateList = getDateList(begin, end);

        //分别求出每天的营业额,存放在list集合中
        ArrayList<Double> list = new ArrayList<>();
        for (LocalDate date : dateList) {
            //一天的起始时间和结束时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            //封装为map集合
            HashMap<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED); //订单状态为已完成才能进行营业额统计
            Double turnover = orderMapper.sumByMap(map);
            //如果从数据库查询不到当天数据，返回给turnover的数据就会为null，为了正常显示数据，所以这里需要处理
            turnover = turnover == null ? 0.0 : turnover;
            list.add(turnover);
        }

        //封装返回结果
        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(list, ","))
                .build();
    }

    /**
     * 用户统计
     *
     * @param: begin;end
     * @return: com.sky.vo.UserReportVO
     */
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getDateList(begin, end);

        List<Object> totalUserList = new ArrayList<>();
        List<Object> newUserlist = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            HashMap<String, Object> map = new HashMap<>();
            //总用户数量
            map.put("end", endTime);
            Integer totalUser = userMapper.countByMap(map);

            //新增用户数量
            map.put("begin", beginTime);
            Integer newUser = userMapper.countByMap(map);

            totalUserList.add(totalUser);
            newUserlist.add(newUser);
        }

        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserlist, ","))
                .build();
    }

    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getDateList(begin, end);

        //查询每天的订单总数
        List<Integer> orderList = new ArrayList<>();
        //查询每天的有效订单数
        List<Integer> validOrderList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            //查询当前天数的订单数及有效订单数
            Integer totalOrder = orderMapper.countByMap(beginTime, endTime, null);
            Integer validOrder = orderMapper.countByMap(beginTime, endTime, Orders.COMPLETED);

            orderList.add(totalOrder);
            validOrderList.add(validOrder);
        }

        //订单总数
        Integer orderCount = 0;
        //有效订单总数
        Integer validOrderCount = 0;
        //订单完成率
        Double orderRate = 0.0;
        for (Integer num : orderList) {
            orderCount += num;
        }
        for (Integer num : validOrderList) {
            validOrderCount += num;
        }
        if (orderCount != 0) {
            orderRate = validOrderCount.doubleValue() / orderCount;
        }

        //封装返回数据
        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderList, ","))
                .validOrderCountList(StringUtils.join(validOrderList, ","))
                .totalOrderCount(orderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderRate)
                .build();
    }

    /**
     * 得到从begin到end的所日期
     *
     * @param: begin，end
     * @return: java.util.List<java.time.LocalDate>
     */
    public static List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> datelist = new ArrayList<>();
        datelist.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            datelist.add(begin);
        }
        return datelist;
    }
}
package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        //将起始日期到结束日期的所有日期添加到集合中
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

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
        List<LocalDate> datelist = new ArrayList<>();
        datelist.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            datelist.add(begin);
        }

        List<Object> totalUserList = new ArrayList<>();
        List<Object> newUserlist = new ArrayList<>();
        for (LocalDate date : datelist) {
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
                .dateList(StringUtils.join(datelist,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .newUserList(StringUtils.join(newUserlist,","))
                .build();
    }
}
package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    /**
     * 营业额统计
     *
     * @param: begin;end
     * @return: com.sky.vo.TurnoverReportVO
     */
    TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end);

    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计指定时间内的销量排名前10
     * @param: begin end
     * @return: com.sky.vo.SalesTop10ReportVO
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}

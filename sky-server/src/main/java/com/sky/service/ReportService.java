package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import java.time.LocalDate;

public interface ReportService {
    /**
     * 营业额统计
     *
     * @param: begin;end
     * @return: com.sky.vo.TurnoverReportVO
     */
    TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end);
}

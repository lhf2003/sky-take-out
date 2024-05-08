package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /***
     * @description: 新增员工
     * @param: employeeDTO
     * @return: void
     */
    void save(EmployeeDTO employeeDTO);

    /***
     * @description: 分页查询
     * @param: employeePageQueryDTO
     * @return: com.sky.result.PageResult
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /***
     * @description: 启用或禁用员工账号
     * @param: status id
     * @return: void
     */
    void statusModify(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}

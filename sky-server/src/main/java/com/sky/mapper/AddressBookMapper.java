package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    @Insert("insert into address_book (user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default)" +
            "values (#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void insert(AddressBook addressBook);

    List<AddressBook> list(AddressBook addressBook);

    void update(AddressBook addressBook);

    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    @Delete("delete from address_book where id = #{id}")
    void delete(Long id);

    /**
     * 将当前用户所有默认地址修改为0
     * @param: addressBook
     * @return: void
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefault(AddressBook addressBook);
}

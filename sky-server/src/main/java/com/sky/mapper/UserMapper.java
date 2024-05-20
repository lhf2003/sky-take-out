package com.sky.mapper;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 查询用户
     *
     * @param: openid
     * @return: com.sky.entity.User
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 新增用户（注册）
     *
     * @param: user
     * @return: void
     */
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User getById(Long id);

    Integer countByMap(Map map);

}

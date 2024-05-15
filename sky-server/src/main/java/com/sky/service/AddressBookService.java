package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    /**
     * 新增地址
     * @param: addressBook
     * @return: void
     */
    void insert(AddressBook addressBook);

    /** 
     * 查询当前登录用户的所有地址信息
     * @param:
     * @return: java.util.List<com.sky.entity.AddressBook>
     */ 
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 根据id修改地址
     * @param: addressBook
     * @return: void
     */
    void update(AddressBook addressBook);

    /**
     * 根据id查询地址
     * @param: id
     * @return: com.sky.entity.AddressBook
     */
    AddressBook getById(Long id);

    /**
     * 根据id删除地址
     * @param: id
     * @return: void
     */
    void delete(Long id);
}

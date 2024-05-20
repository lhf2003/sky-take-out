package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    public void insert(AddressBook addressBook) {
        addressBookMapper.insert(addressBook);
    }

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param:
     * @return: java.util.List<com.sky.entity.AddressBook>
     */
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param: addressBook
     * @return: void
     */
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id查询地址
     *
     * @param: id
     * @return: com.sky.entity.AddressBook
     */
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * 根据id删除地址
     * @param: id
     * @return: void
     */
    public void delete(Long id) {
        addressBookMapper.delete(id);
    }

    /**
     * 设置默认地址
     * @param: addressBook
     * @return: void
     */
    @Transactional
    public void setDefault(AddressBook addressBook) {
        //1、先把当前用户的所有默认地址都设置为0
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefault(addressBook);
        //2、再将前端传递的默认地址设置为1
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}
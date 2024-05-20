package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LHF
 * @version 1.0
 * @date 2024/5/15 14:59
 */
@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "C端-地址簿接口")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result add(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressBookService.insert(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result defaultAddressModify(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> list() {
        log.info("查询当前登录用户的所有地址信息");
        AddressBook addressBook = new AddressBook();
        Long userId = BaseContext.getCurrentId();

        addressBook.setUserId(userId);
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefaultAdress() {
        Long userId = BaseContext.getCurrentId();
        log.info("查询默认地址：{}", BaseContext.getCurrentId());
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(1);
        List<AddressBook> addressBooks = addressBookService.list(addressBook);
        if (addressBooks != null && addressBooks.size() == 1) {
            return Result.success(addressBooks.get(0));
        }
        return Result.error("没有查询到默认地址");
    }

    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result delete(Long id) {
        log.info("根据id删除地址：{}", id);
        addressBookService.delete(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result adressModify(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址：{}", addressBook.getId());
        //TODO 不完整
        addressBookService.update(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getById(@PathVariable Long id) {
        log.info("根据id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }
}
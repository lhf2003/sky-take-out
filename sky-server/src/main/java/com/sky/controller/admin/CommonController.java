package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author LHF
 * @version 1.0
 * @description: TODO
 * @date 2024/5/9 16:24
 */

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    // 在Java的Spring框架中，MultipartFile 是一个接口，它提供了访问上传文件的功能。
    // 这个接口通常用于处理文件上传，比如在web应用程序中，用户可能会上传一个文件，然后这个文件会作为 MultipartFile 对象被处理。
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传");

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //后缀名
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成UUID拼接上原始文件名后缀，防止重复文件名覆盖内容
            String fileName = UUID.randomUUID().toString() + extension;
            String fileUploadPath = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(fileUploadPath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }
        return null;
    }
}
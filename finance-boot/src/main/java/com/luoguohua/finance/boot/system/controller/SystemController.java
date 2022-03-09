package com.luoguohua.finance.boot.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:44
 * Content:
 */
@Api(tags = "系统模块")
@RestController
public class SystemController {

    @GetMapping("test")
    @ApiOperation(value = "系统测试")
    public String testOauth() {
        return "oauth";
    }

}

package com.luoguohua.finance.boot.business.controller;

import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:23
 * Content:
 */
@RestController
@Api(tags = "测试模块")
public class TestController {

    @ApiOperation(value = "业务测试")
    @GetMapping("/business/test")
    public FinanceResponse business(){
        return new FinanceResponse().data("操作成功");
    }
}

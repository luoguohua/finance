package com.luoguohua.finance.boot.business.controller;

import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:23
 * Content:
 */
@RestController
public class TestController {


    @RequestMapping("/business/test")
    public FinanceResponse bussiness(){
        return new FinanceResponse().data("操作成功");
    }
}

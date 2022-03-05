package com.luoguohua.finance.boot.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:44
 * Content:
 */
@RestController
public class SystemController {

    @GetMapping("test")
    public String testOauth() {
        return "oauth";
    }

}

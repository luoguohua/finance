package com.luoguohua.finance.boot.system.controller;

import com.luoguohua.finance.boot.system.service.ValidateCodeService;
import com.luoguohua.finance.common.exception.ValidateCodeException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/14 11:52
 * Content:
 */

@Api(tags = "验证码模块")
@RestController
public class CaptchaController {
    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }
}

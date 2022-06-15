package com.luoguohua.finance.boot.system.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.boot.properties.AuthProperties;
import com.luoguohua.finance.boot.properties.ValidateCodeProperties;
import com.luoguohua.finance.common.constant.FinanceConstant;
import com.luoguohua.finance.common.exception.ValidateCodeException;
import com.luoguohua.finance.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/14 11:40
 * Content:
 * 验证码服务
 */
@Service
public class ValidateCodeService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private AuthProperties properties;

    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        String key = request.getParameter("key");
        if (StrUtil.isBlank(key)) {
            throw new ValidateCodeException("验证码key不能为空");
        }
        ValidateCodeProperties code = properties.getCode();
        setHeader(response, "png");

        ICaptcha captcha = createCaptcha(code);
        redisService.set(FinanceConstant.CODE_PREFIX + key, captcha.getCode().toLowerCase(), code.getTime());
        captcha.write(response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     */
    public void check(String key, String value) throws ValidateCodeException {
        Object codeInRedis = redisService.get(FinanceConstant.CODE_PREFIX + key);
        if (StrUtil.isBlank(value)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StrUtil.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }

    private ICaptcha createCaptcha(ValidateCodeProperties code) {
        ICaptcha captcha = CaptchaUtil.createShearCaptcha(code.getWidth(),code.getHeight(),code.getLength(),4);
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StrUtil.equalsIgnoreCase(type, FinanceConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

}

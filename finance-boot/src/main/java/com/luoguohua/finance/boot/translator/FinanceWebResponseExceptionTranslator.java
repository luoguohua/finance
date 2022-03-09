package com.luoguohua.finance.boot.translator;

import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 10:41
 * Content:
 */
@Slf4j
@Component
public class FinanceWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e){
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        FinanceResponse response = new FinanceResponse();
        String message = "认证失败";
        log.error(message, e);
        if (e instanceof UnsupportedGrantTypeException) {
            message = "不支持该认证类型";
            return status.body(response.message(message));
        }
        if (e instanceof InvalidGrantException) {
            if (StrUtil.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                message = "refresh token无效";
                return status.body(response.message(message));
            }
            if (StrUtil.containsIgnoreCase(e.getMessage(), "locked")) {
                message = "用户已被锁定，请联系管理员";
                return status.body(response.message(message));
            }
            message = "用户名或密码错误";
            return status.body(response.message(message));
        }
        return status.body(response.message(message));
    }
}

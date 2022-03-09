package com.luoguohua.finance.common.handler;

import com.luoguohua.finance.common.exception.FinanceAuthException;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:06
 * Content:
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FinanceResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new FinanceResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = FinanceAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FinanceResponse handleFebsAuthException(FinanceAuthException e) {
        log.error("系统错误", e);
        return new FinanceResponse().message(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FinanceResponse handleAccessDeniedException(){
        return new FinanceResponse().message("没有权限访问该资源");
    }
}

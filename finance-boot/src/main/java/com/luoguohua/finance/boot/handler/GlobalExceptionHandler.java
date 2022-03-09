package com.luoguohua.finance.boot.handler;

import com.luoguohua.finance.common.handler.BaseExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:08
 * Content:
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends BaseExceptionHandler {
}

package com.luoguohua.finance.common.annotation;

import com.luoguohua.finance.common.configure.FinanceLettuceRedisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/14 11:13
 * Content:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FinanceLettuceRedisConfigure.class)
public @interface EnableFinanceLettuceRedis {
}

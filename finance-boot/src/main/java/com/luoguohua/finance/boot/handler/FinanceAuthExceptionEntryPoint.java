package com.luoguohua.finance.boot.handler;

import com.luoguohua.finance.boot.utils.FinanceUtils;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 10:46
 * Content:
 */
@Component
public class FinanceAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        FinanceResponse financeResponse = new FinanceResponse();
        FinanceUtils.makeResponse(
                response, MediaType.APPLICATION_JSON_VALUE,
                HttpServletResponse.SC_UNAUTHORIZED, financeResponse.message("token无效")
        );
    }
}

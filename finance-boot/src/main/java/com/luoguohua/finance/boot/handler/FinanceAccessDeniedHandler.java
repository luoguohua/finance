package com.luoguohua.finance.boot.handler;

import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:03
 * Content:
 */
@Component
public class FinanceAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        FinanceResponse financeResponse = new FinanceResponse();
        FinanceUtils.makeResponse(
                httpServletResponse, MediaType.APPLICATION_JSON_VALUE,
                HttpServletResponse.SC_FORBIDDEN, financeResponse.message("没有权限访问该资源"));
    }
}

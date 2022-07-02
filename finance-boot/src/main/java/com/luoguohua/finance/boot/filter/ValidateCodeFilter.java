package com.luoguohua.finance.boot.filter;

import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.boot.properties.AuthProperties;
import com.luoguohua.finance.boot.system.service.ValidateCodeService;
import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.common.exception.ValidateCodeException;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 14:09
 * Content:
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private AuthProperties authProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        RequestMatcher matcher = new AntPathRequestMatcher("/oauth/token", HttpMethod.POST.toString());
        if (matcher.matches(httpServletRequest)
                && StrUtil.equalsIgnoreCase(httpServletRequest.getParameter("grant_type"), "password")
                && authProperties.getCode().getEnable()) {
            try {
                validateCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (ValidateCodeException e) {
                FinanceResponse financeResponse = new FinanceResponse();
                FinanceUtils.makeResponse(httpServletResponse, MediaType.APPLICATION_JSON_UTF8_VALUE,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, financeResponse.message(e.getMessage()));
                log.error(e.getMessage(), e);
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private void validateCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("key");
//        validateCodeService.check(key, code);
    }
}

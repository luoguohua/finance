package com.luoguohua.finance.boot.system.controller;

import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.common.exception.FinanceAuthException;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:44
 * Content:
 */
@RestController
public class UserController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }


    @DeleteMapping("signout")
    public FinanceResponse signout(HttpServletRequest request) throws FinanceAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StrUtil.replace(authorization, "bearer ", "");
        FinanceResponse financeResponse = new FinanceResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new FinanceAuthException("退出登录失败");
        }
        return financeResponse.message("退出登录成功");
    }

}

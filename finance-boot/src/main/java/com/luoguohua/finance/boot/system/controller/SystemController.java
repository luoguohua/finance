package com.luoguohua.finance.boot.system.controller;

import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.common.exception.FinanceAuthException;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:44
 * Content:
 */
@Api(tags = "系统模块")
@RestController
public class SystemController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("current-user")
    @ApiOperation(value = "获取用户信息")
    public Principal currentUser(Principal principal) {
        return principal;
    }


    @DeleteMapping("sign-out")
    @ApiOperation(value = "退出登录")
    public FinanceResponse signout(HttpServletRequest request) throws FinanceAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StrUtil.replace(authorization, "Bearer ", "");
        FinanceResponse financeResponse = new FinanceResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new FinanceAuthException("退出登录失败");
        }
        return financeResponse.message("退出登录成功");
    }

}

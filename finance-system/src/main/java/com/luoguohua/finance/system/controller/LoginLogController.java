package com.luoguohua.finance.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.system.po.LoginLog;
import com.luoguohua.finance.system.service.ILoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("loginLog")
public class LoginLogController {

    private final ILoginLogService loginLogService;

    @GetMapping
    public FinanceResponse loginLogList(LoginLog loginLog, QueryRequest request) {
        Map<String, Object> dataTable = FinanceUtils.getDataTable(this.loginLogService.findLoginLogs(loginLog, request));
        return new FinanceResponse().data(dataTable);
    }

    @GetMapping("currentUser")
    public FinanceResponse getUserLastSevenLoginLogs() {
        String currentUsername = FinanceUtils.getCurrentUsername();
        List<LoginLog> userLastSevenLoginLogs = this.loginLogService.findUserLastSevenLoginLogs(currentUsername);
        return new FinanceResponse().data(userLastSevenLoginLogs);
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('loginlog:delete')")
    public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] loginLogIds = ids.split(StringPool.COMMA);
        this.loginLogService.deleteLoginLogs(loginLogIds);
    }

}

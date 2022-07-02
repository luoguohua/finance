package com.luoguohua.finance.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.service.IMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 13:41
 * Content:
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单模块")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("/{username}")
    public FinanceResponse getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        Map<String, Object> result = new HashMap<>(4);
        List<VueRouter<SysMenu>> userRouters = this.menuService.getUserRouters(username);
        String userPermissions = this.menuService.findUserPermissions(username);
        String[] permissionArray = new String[0];
        if (StringUtils.isNoneBlank(userPermissions)) {
            permissionArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(userPermissions, StringPool.COMMA);
        }
        result.put("routes", userRouters);
        result.put("permissions", permissionArray);
        return new FinanceResponse().data(result);
    }

    @GetMapping
    public FinanceResponse menuList(SysMenu menu) {
        Map<String, Object> menus = this.menuService.findMenus(menu);
        return new FinanceResponse().data(menus);
    }

    @GetMapping("/permissions")
    public String findUserPermissions(String username) {
        return this.menuService.findUserPermissions(username);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('menu:add')")
    public void addMenu(@Valid SysMenu menu) {
        this.menuService.createMenu(menu);
    }

    @DeleteMapping("/{menuIds}")
    @PreAuthorize("hasAuthority('menu:delete')")
    public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        String[] ids = menuIds.split(StringPool.COMMA);
        this.menuService.deleteMenus(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('menu:update')")
    public void updateMenu(@Valid SysMenu menu) {
        this.menuService.updateMenu(menu);
    }


}

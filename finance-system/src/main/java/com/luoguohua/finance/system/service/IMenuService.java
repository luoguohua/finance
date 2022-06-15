package com.luoguohua.finance.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.system.po.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:57
 * Content:
 */
public interface IMenuService extends IService<SysMenu> {

    /**
     * 通过用户名查询用户权限信息
     *
     * @param username 用户名
     * @return 权限信息
     */
    Set<String> findUserPermissions(String username);

    /**
     * 通过用户名创建对应的 Vue路由信息
     *
     * @param username 用户名
     * @return 路由信息
     */
    List<VueRouter<SysMenu>> getUserRouters(String username);
}

package com.luoguohua.finance.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.system.po.SysMenu;

import java.util.List;
import java.util.Map;
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
    String findUserPermissions(String username);

    /**
     * 通过用户名创建对应的 Vue路由信息
     *
     * @param username 用户名
     * @return 路由信息
     */
    List<VueRouter<SysMenu>> getUserRouters(String username);

    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<SysMenu> findUserMenus(String username);

    /**
     * 获取用户菜单
     *
     * @param menu menu
     * @return 用户菜单
     */
    Map<String, Object> findMenus(SysMenu menu);

    /**
     * 获取菜单列表
     *
     * @param menu menu
     * @return 菜单列表
     */
    List<SysMenu> findMenuList(SysMenu menu);

    /**
     * 创建菜单
     *
     * @param menu menu
     */
    void createMenu(SysMenu menu);

    /**
     * 更新菜单
     *
     * @param menu menu
     */
    void updateMenu(SysMenu menu);

    /**
     * 递归删除菜单/按钮
     *
     * @param menuIds menuIds
     */
    void deleteMenus(String[] menuIds);
}

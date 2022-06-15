package com.luoguohua.finance.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoguohua.finance.common.router.RouterMeta;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.common.utils.TreeUtil;
import com.luoguohua.finance.system.mapper.MenuMapper;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:59
 * Content:
 */
@Slf4j
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, SysMenu> implements IMenuService {

    @Override
    public Set<String> findUserPermissions(String username) {
        List<SysMenu> userPermissions = this.baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(SysMenu::getPerms).collect(Collectors.toSet());
    }

    @Override
    public List<VueRouter<SysMenu>> getUserRouters(String username) {
        List<VueRouter<SysMenu>> routes = new ArrayList<>();
        List<SysMenu> menus = this.baseMapper.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<SysMenu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon()));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }
}

package com.luoguohua.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoguohua.finance.common.constant.PageConstant;
import com.luoguohua.finance.common.exception.FinanceException;
import com.luoguohua.finance.system.dto.MenuTree;
import com.luoguohua.finance.common.pojo.dto.Tree;
import com.luoguohua.finance.common.router.RouterMeta;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.common.utils.TreeUtil;
import com.luoguohua.finance.system.mapper.MenuMapper;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    public String findUserPermissions(String username) {
        checkUser(username);
        List<SysMenu> userPermissions = this.baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(SysMenu::getPerms).collect(Collectors.joining(StringPool.COMMA));
    }

    @Override
    public List<VueRouter<SysMenu>> getUserRouters(String username) {
        checkUser(username);
        List<VueRouter<SysMenu>> routes = new ArrayList<>();
        List<SysMenu> menus = this.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<SysMenu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon(), true));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    @Override
    public List<SysMenu> findUserMenus(String username) {
        checkUser(username);
        return this.baseMapper.findUserMenus(username);
    }

    @Override
    public Map<String, Object> findMenus(SysMenu menu) {
        Map<String, Object> result = new HashMap<>(4);
        try {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByAsc(SysMenu::getOrderNum);
            List<SysMenu> menus = baseMapper.selectList(queryWrapper);

            List<MenuTree> trees = new ArrayList<>();
            buildTrees(trees, menus);

            if (StringUtils.equals(menu.getType(), SysMenu.TYPE_BUTTON)) {
                result.put(PageConstant.ROWS, trees);
            } else {
                List<? extends Tree<?>> menuTree = TreeUtil.build(trees);
                result.put(PageConstant.ROWS, menuTree);
            }

            result.put("total", menus.size());
        } catch (NumberFormatException e) {
            log.error("查询菜单失败", e);
            result.put(PageConstant.ROWS, null);
            result.put(PageConstant.TOTAL, 0);
        }
        return result;
    }

    @Override
    public List<SysMenu> findMenuList(SysMenu menu) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.like(SysMenu::getMenuName, menu.getMenuName());
        }
        queryWrapper.orderByAsc(SysMenu::getMenuId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(SysMenu menu) {
        menu.setCreateTime(new Date());
        setMenu(menu);
        this.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenu menu) {
        menu.setModifyTime(new Date());
        setMenu(menu);
        baseMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenus(String[] menuIds) {
        this.delete(Arrays.asList(menuIds));
    }

    private void buildTrees(List<MenuTree> trees, List<SysMenu> menus) {
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setLabel(menu.getMenuName());
            tree.setComponent(menu.getComponent());
            tree.setIcon(menu.getIcon());
            tree.setOrderNum(menu.getOrderNum());
            tree.setPath(menu.getPath());
            tree.setType(menu.getType());
            tree.setPerms(menu.getPerms());
            trees.add(tree);
        });
    }

    private void setMenu(SysMenu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(SysMenu.TOP_MENU_ID);
        }
        if (SysMenu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setPath(null);
            menu.setIcon(null);
            menu.setComponent(null);
            menu.setOrderNum(null);
        }
    }

    private void delete(List<String> menuIds) {
        removeByIds(menuIds);

        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getParentId, menuIds);
        List<SysMenu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            this.delete(menuIdList);
        }
    }

    private void checkUser(String username) {
        String currentUsername = FinanceUtils.getCurrentUsername();
        if (StringUtils.isNotBlank(currentUsername)
                && !StringUtils.equalsIgnoreCase(currentUsername, username)) {
            throw new FinanceException("无权获取别的用户数据");
        }
    }


}

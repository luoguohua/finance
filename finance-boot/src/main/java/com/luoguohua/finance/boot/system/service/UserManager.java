package com.luoguohua.finance.boot.system.service;

import com.luoguohua.finance.system.mapper.MenuMapper;
import com.luoguohua.finance.system.mapper.UserMapper;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/9 18:17
 * Content: 用户管理服务
 */
@Service
public class UserManager {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;


    public SysUser findByName(String username) {
        return userMapper.findByName(username);
    }

    public String findUserPermissions(String username) {
        List<SysMenu> userPermissions = menuMapper.findUserPermissions(username);

        return userPermissions.stream().map(SysMenu::getPerms).collect(Collectors.joining(","));
    }

}

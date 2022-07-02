package com.luoguohua.finance.boot.system.service;

import com.luoguohua.finance.common.constant.FinanceConstant;
import com.luoguohua.finance.system.mapper.MenuMapper;
import com.luoguohua.finance.system.mapper.UserMapper;
import com.luoguohua.finance.system.mapper.UserRoleMapper;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.po.SysUser;
import com.luoguohua.finance.system.po.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private UserRoleMapper userRoleMapper;


    public SysUser findByName(String username) {
        return userMapper.findByName(username);
    }

    public String findUserPermissions(String username) {
        List<SysMenu> userPermissions = menuMapper.findUserPermissions(username);

        return userPermissions.stream().map(SysMenu::getPerms).collect(Collectors.joining(","));
    }


    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SysUser registerUser(String username, String password) {
        SysUser systemUser = new SysUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        systemUser.setCreateTime(new Date());
        systemUser.setStatus(SysUser.STATUS_VALID);
        systemUser.setSex(SysUser.SEX_UNKNOW);
        systemUser.setAvatar(SysUser.DEFAULT_AVATAR);
        systemUser.setDescription("注册用户");
        this.userMapper.insert(systemUser);

        UserRole userRole = new UserRole();
        userRole.setUserId(systemUser.getUserId());
        // 注册用户角色 ID
        userRole.setRoleId(FinanceConstant.REGISTER_ROLE_ID);
        this.userRoleMapper.insert(userRole);
        return systemUser;
    }
}

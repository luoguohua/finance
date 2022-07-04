package com.luoguohua.finance.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoguohua.finance.common.exception.FinanceException;
import com.luoguohua.finance.common.pojo.dto.CurrentUser;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.common.router.RouterMeta;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.common.utils.TreeUtil;
import com.luoguohua.finance.system.mapper.MenuMapper;
import com.luoguohua.finance.system.mapper.UserMapper;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.po.SysUser;
import com.luoguohua.finance.system.po.UserRole;
import com.luoguohua.finance.system.service.IMenuService;
import com.luoguohua.finance.system.service.IUserRoleService;
import com.luoguohua.finance.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<SysUser> findUserDetail(SysUser user, QueryRequest request) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findUserDetailPage(page, user);
    }

    @Override
    public void createUser(SysUser user) {
        // 创建用户
        user.setCreateTime(DateUtil.date());
        user.setAvatar(SysUser.DEFAULT_AVATAR);
        user.setPassword(passwordEncoder.encode(SysUser.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    public void updateUser(SysUser user) {
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        user.setCreateTime(null);
        user.setModifyTime(DateUtil.date());
        updateById(user);

        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        removeByIds(list);
        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    private void setUserRoles(SysUser user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            userRoleService.save(ur);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String username) {
        SysUser user = new SysUser();
        user.setLastLoginTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    @Override
    public IPage<SysUser> findUserDetailList(SysUser user, QueryRequest request) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findUserDetailPage(page, user);
    }

    @Override
    public SysUser findByName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateProfile(SysUser user) throws FinanceException {
        user.setPassword(null);
        user.setUsername(null);
        user.setStatus(null);
        if (isCurrentUser(user.getUserId())) {
            updateById(user);
        } else {
            throw new FinanceException("您无权修改别人的账号信息！");
        }
    }

    private boolean isCurrentUser(Long id) {
        CurrentUser currentUser = FinanceUtils.getCurrentUser();
        return currentUser != null && id.equals(currentUser.getUserId());
    }

    @Override
    public void updateSidebarTheme(String theme) {
        CurrentUser currentUser = FinanceUtils.getCurrentUser();
        if (currentUser != null) {
            Long userId = currentUser.getUserId();
            SysUser user = new SysUser();
            user.setUserId(userId);
            user.setTheme(theme);
            baseMapper.updateById(user);
        }
    }

    @Override
    public void updateAvatar(String avatar) {
        SysUser user = new SysUser();
        user.setAvatar(avatar);
        String currentUsername = FinanceUtils.getCurrentUsername();
        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, currentUsername));
    }

    @Override
    public void updatePassword(String password) {
        SysUser user = new SysUser();
        user.setPassword(passwordEncoder.encode(password));
        String currentUsername = FinanceUtils.getCurrentUsername();
        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, currentUsername));
    }

    @Override
    public void resetPassword(String[] userNames) {
        SysUser params = new SysUser();
        params.setPassword(passwordEncoder.encode(MD5.create().digestHex(SysUser.DEFAULT_PASSWORD)));
        List<String> list = Arrays.asList(userNames);
        this.baseMapper.update(params, new LambdaQueryWrapper<SysUser>().in(SysUser::getUsername, list));
    }

}

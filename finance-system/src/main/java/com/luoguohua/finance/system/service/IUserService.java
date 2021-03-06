package com.luoguohua.finance.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.common.exception.FinanceException;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.common.router.VueRouter;
import com.luoguohua.finance.system.po.SysMenu;
import com.luoguohua.finance.system.po.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:57
 * Content:
 */
public interface IUserService extends IService<SysUser> {

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param user    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<SysUser> findUserDetail(SysUser user, QueryRequest request);

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds);

    /**
     * 更新用户登录时间
     *
     * @param username username
     */
    void updateLoginTime(String username);

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param user    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<SysUser> findUserDetailList(SysUser user, QueryRequest request);
    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser findByName(String username);

    /**
     * 更新个人信息
     *
     * @param user 个人信息
     * @throws FinanceException 异常
     */
    void updateProfile(SysUser user) throws FinanceException;

    /**
     * 更新用户侧边栏主题
     *
     * @param theme 侧栏主题
     */
    void updateSidebarTheme(String theme);

    /**
     * 更新用户头像
     *
     * @param avatar 用户头像
     */
    void updateAvatar(String avatar);

    /**
     * 更新用户密码
     *
     * @param password 新密码
     */
    void updatePassword(String password);

    /**
     * 重置密码
     *
     * @param userNames 用户集合
     */
    void resetPassword(String[] userNames);

}

package com.luoguohua.finance.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.system.po.UserRole;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:57
 * Content:
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 删除角色用户关联关系
     * @param roleIds
     */
    void deleteUserRolesByRoleId(String[] roleIds);

    /**
     * 删除角色用户关联关系
     * @param userIds
     */
    void deleteUserRolesByUserId(String[] userIds);
}

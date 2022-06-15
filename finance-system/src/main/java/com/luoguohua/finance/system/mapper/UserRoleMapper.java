package com.luoguohua.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luoguohua.finance.system.po.UserRole;
import org.apache.ibatis.annotations.Param;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 14:36
 * Content:
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户 ID
     * @return boolean
     */
    Boolean deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据角色Id删除该角色的用户关系
     *
     * @param roleId 角色 ID
     * @return boolean
     */
    Boolean deleteByRoleId(@Param("roleId") Long roleId);
}
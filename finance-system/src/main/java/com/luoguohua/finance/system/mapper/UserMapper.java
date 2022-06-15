package com.luoguohua.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoguohua.finance.system.po.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/9 18:09
 * Content:
 */
public interface UserMapper extends BaseMapper<SysUser> {


    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    SysUser findByName(String username);


    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<SysUser> findUserDetailPage(Page page, @Param("user") SysUser user);
}

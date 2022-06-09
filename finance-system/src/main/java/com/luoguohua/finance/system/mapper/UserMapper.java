package com.luoguohua.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luoguohua.finance.system.po.SysUser;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/9 18:09
 * Content:
 */
public interface UserMapper extends BaseMapper<SysUser> {
    SysUser findByName(String username);
}

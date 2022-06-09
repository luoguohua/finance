package com.luoguohua.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luoguohua.finance.system.po.SysMenu;

import java.util.List;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/9 18:10
 * Content:
 */
public interface MenuMapper  extends BaseMapper<SysMenu> {

    List<SysMenu> findUserPermissions(String username);
}

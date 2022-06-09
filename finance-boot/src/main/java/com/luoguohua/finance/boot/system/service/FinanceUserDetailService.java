package com.luoguohua.finance.boot.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.common.pojo.dto.FinanceUser;
import com.luoguohua.finance.system.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:35
 * Content:
 * 用户名密码验证服务
 */
@Service
public class FinanceUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser systemUser = userManager.findByName(username);
        if (systemUser != null) {
            String permissions = userManager.findUserPermissions(systemUser.getUsername());
            boolean notLocked = false;
            if (StrUtil.equals(SysUser.STATUS_VALID, systemUser.getStatus())){
                notLocked = true;
            }
            FinanceUser authUser = new FinanceUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
            BeanUtil.copyProperties(systemUser,authUser, CopyOptions.create().ignoreNullValue());
            return authUser ;
        } else {
            throw new UsernameNotFoundException("");
        }
    }

}

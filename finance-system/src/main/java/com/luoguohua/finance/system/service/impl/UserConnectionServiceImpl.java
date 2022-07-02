package com.luoguohua.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoguohua.finance.system.mapper.UserConnectionMapper;
import com.luoguohua.finance.system.po.UserConnection;
import com.luoguohua.finance.system.service.UserConnectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MrBird
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserConnectionServiceImpl extends ServiceImpl<UserConnectionMapper, UserConnection> implements UserConnectionService {

    @Override
    public UserConnection selectByCondition(String providerName, String providerUserId) {
        LambdaQueryWrapper<UserConnection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserConnection::getProviderName, providerName)
                .eq(UserConnection::getProviderUserId, providerUserId);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<UserConnection> selectByCondition(String username) {
        LambdaQueryWrapper<UserConnection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserConnection::getUserName, username);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserConnection(UserConnection userConnection) {
        this.baseMapper.insert(userConnection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCondition(String username, String providerName) {
        LambdaQueryWrapper<UserConnection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserConnection::getUserName, username);
        queryWrapper.eq(UserConnection::getProviderName, providerName);
        this.remove(queryWrapper);
    }

}

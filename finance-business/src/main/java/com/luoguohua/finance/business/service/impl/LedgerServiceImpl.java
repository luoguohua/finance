package com.luoguohua.finance.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoguohua.finance.business.mapper.LedgerMapper;
import com.luoguohua.finance.business.po.Ledger;
import com.luoguohua.finance.business.service.ILedgerService;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:59
 * Content:
 */
@Slf4j
@Service("ledgerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LedgerServiceImpl extends ServiceImpl<LedgerMapper, Ledger> implements ILedgerService {


    @Override
    public IPage<Ledger> findLedgers(Ledger ledger, QueryRequest request) {
        QueryWrapper<Ledger> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(ledger.getLedgerName())) {
            queryWrapper.lambda().like(Ledger::getLedgerName,ledger.getLedgerName());
        }
        queryWrapper.lambda().orderByDesc(Ledger::getCreateTime);
        Page<Ledger> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }
}

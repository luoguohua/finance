package com.luoguohua.finance.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.business.po.Ledger;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:57
 * Content:
 */
public interface ILedgerService extends IService<Ledger> {


    /**
     * 列表查询
     * @param ledger
     * @param request
     * @return
     */
    IPage<Ledger> findLedgers(Ledger ledger, QueryRequest request);
}

package com.luoguohua.finance.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.business.po.Ledger;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;

import java.util.List;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:57
 * Content:
 */
public interface ILedgerService extends IService<Ledger> {


    /**
     * 列表查询(分页)
     * @param ledger
     * @param request
     * @return
     */
    IPage<Ledger> findLedgerPages(Ledger ledger, QueryRequest request);

    /**
     * 列表查询(不分页)
     * @param ledger
     * @return
     */
    List<Ledger> findLedgers(Ledger ledger);
}

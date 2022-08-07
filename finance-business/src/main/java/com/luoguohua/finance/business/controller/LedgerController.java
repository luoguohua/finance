package com.luoguohua.finance.business.controller;

import cn.hutool.json.JSONUtil;
import com.luoguohua.finance.business.po.Ledger;
import com.luoguohua.finance.business.service.ILedgerService;
import com.luoguohua.finance.business.vo.LedgerVo;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.common.utils.FinanceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author luoguohua
 */
@Slf4j
@RestController
@RequestMapping("ledger")
public class LedgerController {


    @Autowired
    private ILedgerService ledgerService;

    @GetMapping("/list")
    public FinanceResponse ledgerList(Ledger ledger, QueryRequest request) {
        Map<String, Object> dataTable = FinanceUtils.getDataTable(this.ledgerService.findLedgerPages(ledger, request));
        return new FinanceResponse().data(dataTable);
    }

    @GetMapping
    public FinanceResponse ledgerList(Ledger ledger) {
        return new FinanceResponse().data(this.ledgerService.findLedgers(ledger));
    }

    @PostMapping
    public FinanceResponse addLedger(@Validated LedgerVo vo){
        System.out.println("JSONUtil.toJsonStr(vo) = " + JSONUtil.toJsonStr(vo));
        return new FinanceResponse();
    }

}

package com.luoguohua.finance.boot.business.ledger;


import cn.hutool.core.date.DateUtil;
import com.luoguohua.finance.boot.FinanceBootApplicationTests;
import com.luoguohua.finance.business.po.Ledger;
import com.luoguohua.finance.business.service.ILedgerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/7/5 14:00
 * Content:
 */
public class LedgerTest extends FinanceBootApplicationTests {

    @Autowired
    private ILedgerService ledgerService;

    @Test
    public void testInsert(){
        Ledger ledger = new Ledger();
        ledger.setLedgerName("默认账本");
        ledger.setAssessedMonthlyIncome(new BigDecimal("10000"));
        ledger.setCreateTime(DateUtil.date());
        ledgerService.save(ledger);
    }
}

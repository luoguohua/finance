package com.luoguohua.finance.business.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luoguohua
 */
@Data
@TableName("t_ledger_budget_category")
public class LedgerBudgetCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账本id
     */
    @TableField("ledger_id")
    private Long ledgerId;

    /**
     *分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     *分类编码
     */
    @TableField("category_code")
    private String categoryCode;

    /**
     *预算比例
     */
    @TableField("budget_proportion")
    private BigDecimal budgetProportion;


    /**
     *预算金额
     */
    @TableField("budget_amounts")
    private BigDecimal budgetAmounts;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;


}

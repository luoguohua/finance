package com.luoguohua.finance.business.vo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class LedgerBudgetCategoryVo {

    /**
     * ID
     */
    private Long id;

    /**
     *分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    /**
     *分类编码
     */
    @NotBlank(message = "分类编码不能为空")
    private String categoryCode;

    /**
     *预算比例
     */
    @NotNull(message = "预算比例不能为空")
    private BigDecimal budgetProportion;


    /**
     *预算金额
     */
    @NotNull(message = "预算金额不能为空")
    private BigDecimal budgetAmounts;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}

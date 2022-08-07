package com.luoguohua.finance.business.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("账本页面对象")
public class LedgerVo {

    /**
     *id
     */
    private Long id;

    /**
     * 账本名称
     */
    @NotBlank(message = "账本名称不能为空")
    private String ledgerName;

    /**
     * 评估月收入
     */
    @NotNull(message = "评估月收入不能为空")
    private BigDecimal assessedMonthlyIncome;

    /**
     * 账本分类
     */
    private List<LedgerBudgetCategoryVo> budgetCategories;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}

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
@TableName("t_ledger_label")
public class LedgerLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @TableField("ledger_id")
    private String ledgerId;

    /**
     * 标签类型：{I} 收入；{O}支出
     */
    @TableField("label_type")
    private String labelType;

    /**
     * 标签名称
     */
    @TableField("label_name")
    private String labelName;

    /**
     * 标签编码
     */
    @TableField("label_code")
    private Integer labelCode;


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

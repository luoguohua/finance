package com.luoguohua.finance.business.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luoguohua
 */
@Data
@TableName("t_ledger")
public class LedgerRecord implements Serializable {

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
    private String ledgerId;

    /**
     * 标签类型：{I} 收入；{O}支出
     */
    @TableField("label_type")
    private String labelType;

    /**
     * 标签编码
     */
    @TableField("label_code")
    private Integer labelCode;

    /**
     * 金额
     */
    @TableField("amounts")
    private BigDecimal amounts;

    /**
     * 记录人
     */
    @TableField("recorder")
    private String recorder;

    /**
     * 记账时间
     */
    @TableField("record_time")
    private Date recordTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 修改人
     */
    @TableField(value = "modify_user", fill = FieldFill.UPDATE)
    private String modifyUser;

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

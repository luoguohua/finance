package com.luoguohua.finance.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author MrBird
 */
@Data
@TableName("sys_dept")
public class SysDept implements Serializable {

    public static final Long TOP_DEPT_ID = 0L;
    private static final long serialVersionUID = -7790334862410409053L;
    @TableId(value = "DEPT_ID", type = IdType.AUTO)
    private Long deptId;

    @TableField(value = "PARENT_ID")
    private Long parentId;

    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String deptName;

    @TableField(value = "ORDER_NUM")
    private Integer orderNum;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "MODIFY_TIME")
    private Date modifyTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}
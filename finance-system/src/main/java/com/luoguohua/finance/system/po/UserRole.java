package com.luoguohua.finance.system.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 14:35
 * Content:
 */
@Data
@TableName("sys_user_role")
public class UserRole {

    private static final long serialVersionUID = -3166012934498268403L;

    @TableField(value = "USER_ID")
    private Long userId;

    @TableField(value = "ROLE_ID")
    private Long roleId;
}

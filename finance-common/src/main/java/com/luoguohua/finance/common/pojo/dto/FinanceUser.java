package com.luoguohua.finance.common.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:38
 * Content:
 */
@Data
public class FinanceUser implements Serializable {

    private String username;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked= true;

    private boolean credentialsNonExpired= true;

    private boolean enabled= true;
}

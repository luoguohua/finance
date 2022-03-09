package com.luoguohua.finance.boot.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 9:50
 * Content:
 */
@Data
public class ClientsProperties {
    private String client;
    private String secret;
    private String grantType = "password,refresh_token";
    private String scope = "all";
}

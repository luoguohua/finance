package com.luoguohua.finance.boot.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 9:51
 * Content:
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "finance.auth")
public class AuthProperties {

    private ClientsProperties[] clients = {};
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();
}

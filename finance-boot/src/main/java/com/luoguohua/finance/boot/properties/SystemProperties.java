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
@ConfigurationProperties(prefix = "finance.system")
public class SystemProperties {


    private String anonUrl;

    private SwaggerProperties swagger = new SwaggerProperties();

}

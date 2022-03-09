package com.luoguohua.finance.boot.properties;

import lombok.Data;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:38
 * Content:
 */
@Data
public class SwaggerProperties {
    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String author;
    private String url;
    private String email;
    private String license;
    private String licenseUrl;
}

package com.luoguohua.finance.boot.configure;

import com.luoguohua.finance.boot.properties.SwaggerProperties;
import com.luoguohua.finance.boot.properties.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:19
 * Content:
 */
@Configuration
@EnableSwagger2WebMvc
public class FinanceKnife4jConfigure {

    @Autowired
    private SystemProperties systemProperties;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        SwaggerProperties swaggerProperties = systemProperties.getSwagger();
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerProperties))
                //分组名称
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swagger) {
        return new ApiInfo(
                swagger.getTitle(),
                swagger.getDescription(),
                swagger.getVersion(),
                null,
                new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()),
                swagger.getLicense(), swagger.getLicenseUrl(), Collections.emptyList());
    }
}

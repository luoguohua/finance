package com.luoguohua.finance.boot.configure;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luoguohua.finance.boot.properties.SwaggerProperties;
import com.luoguohua.finance.boot.properties.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collections;
import java.util.function.Predicate;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:19
 * Content:
 */
@Configuration
@EnableSwagger2
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
                .apis(getPredicate(swaggerProperties)
                        .or(RequestHandlerSelectors.withClassAnnotation(FrameworkEndpoint.class)))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 针对多包扫描的特殊处理
     * @param swaggerProperties
     * @return
     */
    private Predicate<RequestHandler> getPredicate(SwaggerProperties swaggerProperties){
        String[] packageUrls = swaggerProperties.getBasePackage().split(StringPool.COMMA);
        if(packageUrls.length > 1){
            Predicate<RequestHandler> predicate = null;
            for (int i = 0; i < packageUrls.length; i++) {
                if(predicate == null){
                    predicate = RequestHandlerSelectors.basePackage(packageUrls[i]);
                }
                else{
                    predicate = predicate.or(RequestHandlerSelectors.basePackage(packageUrls[i]));
                }
            }
            return predicate;
        }
        else if(packageUrls.length == 1){
            return RequestHandlerSelectors.basePackage(packageUrls[0]);
        }
        else{
            return RequestHandlerSelectors.basePackage("");
        }
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

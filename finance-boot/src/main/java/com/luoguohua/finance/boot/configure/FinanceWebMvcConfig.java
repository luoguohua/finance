package com.luoguohua.finance.boot.configure;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:13
 * Content:
 */
@SpringBootConfiguration
public class FinanceWebMvcConfig {

    @Bean
    public CorsFilter corsFilter() {
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration config = new CorsConfiguration();
        //设置放行哪些原始域
        config.addAllowedOrigin("*");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        /**
         * @Date :2022/03/09
         * 暴露哪些头部信息,头信息不能使用“*”，来代替暴露所有头信息，必须指定要暴露的头信息
         * config.addExposedHeader("*");
         */
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("X-Requested-With");
        config.addExposedHeader("accept");
        config.addExposedHeader("Origin");
        config.addExposedHeader("Access-Control-Request-Method");
        config.addExposedHeader("Access-Control-Request-Headers");
        /**
         * @Date :2022/03/09
         * 放行哪些请求方式
         * config.addAllowedMethod("GET");     //get
         * config.addAllowedMethod("PUT");     //put
         * config.addAllowedMethod("POST");    //post
         * config.addAllowedMethod("DELETE");  //delete
         */
        //放行全部请求
        config.addAllowedMethod("*");
        //是否发送Cookie
        config.setAllowCredentials(true);

        //2. 添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        //返回CorsFilter
        return new CorsFilter(corsConfigurationSource);
    }
}

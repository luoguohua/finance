package com.luoguohua.finance.boot.configure;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 11:13
 * Content:
 */
@SpringBootConfiguration
@Order(0)
public class FinanceWebMvcConfig {

    @Bean
    public CorsFilter corsFilter() {
//        //创建CorsConfiguration对象后添加配置
//        CorsConfiguration config = new CorsConfiguration();
//        //设置放行哪些原始域
//        config.addAllowedOrigin(CorsConfiguration.ALL);
//        //放行哪些原始请求头部信息
//        config.addAllowedHeader(CorsConfiguration.ALL);
//        /**
//         * @Date :2022/03/09
//         * 暴露哪些头部信息,头信息不能使用“*”，来代替暴露所有头信息，必须指定要暴露的头信息
//         * config.addExposedHeader("*");
//         */
////        config.addExposedHeader("Content-Type");
////        config.addExposedHeader("X-Requested-With");
////        config.addExposedHeader("accept");
////        config.addExposedHeader("Origin");
////        config.addExposedHeader("Access-Control-Request-Method");
////        config.addExposedHeader("Access-Control-Request-Headers");
////        config.setMaxAge(3600L);
//        /**
//         * @Date :2022/03/09
//         * 放行哪些请求方式
//         * config.addAllowedMethod("GET");     //get
//         * config.addAllowedMethod("PUT");     //put
//         * config.addAllowedMethod("POST");    //post
//         * config.addAllowedMethod("DELETE");  //delete
//         */
//        //放行全部请求
//        config.addAllowedMethod(CorsConfiguration.ALL);
//        //是否发送Cookie
//        config.setAllowCredentials(true);
//
//        //2. 添加映射路径
//        UrlBasedCorsConfigurationSource corsConfigurationSource =
//                new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", config);
//        //返回CorsFilter
//        return new CorsFilter(corsConfigurationSource);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }
}

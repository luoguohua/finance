package com.luoguohua.finance.boot.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 13:48
 * Content:
 * 资源服务配置类
 */
@Configuration
@EnableResourceServer
public class FinanceResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().requestMatchers().antMatchers("/**")
                .and().authorizeRequests().antMatchers("/**")
                .authenticated();
    }
}

package com.luoguohua.finance.boot.configure;

import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.boot.handler.FinanceAccessDeniedHandler;
import com.luoguohua.finance.boot.handler.FinanceAuthExceptionEntryPoint;
import com.luoguohua.finance.boot.properties.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

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

    @Autowired
    private FinanceAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private FinanceAuthExceptionEntryPoint authExceptionEntryPoint;

    @Autowired
    private SystemProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StrUtil.splitToArray(properties.getAnonUrl(), ",");
        http.csrf().disable().requestMatchers().antMatchers("/**")
                .and().authorizeRequests()
                .antMatchers(anonUrls)
                .permitAll()
                .antMatchers("/**")
                .authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authExceptionEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }
}

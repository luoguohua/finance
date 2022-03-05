package com.luoguohua.finance.boot.configure;

import com.luoguohua.finance.boot.system.service.FinanceUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 13:42
 * Content:
 * 安全配置类
 */
@Order(2)
@EnableWebSecurity
public class FinanceSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private FinanceUserDetailService financeUserDetailService;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/oauth/**")
                .and().authorizeRequests().antMatchers("/oauth/**").authenticated()
                .and().csrf().disable();
    }
}

package com.luoguohua.finance.boot.configure;

import com.luoguohua.finance.boot.filter.ValidateCodeFilter;
import com.luoguohua.finance.boot.handler.FinanceWebLoginFailureHandler;
import com.luoguohua.finance.boot.handler.FinanceWebLoginSuccessHandler;
import com.luoguohua.finance.boot.properties.SystemProperties;
import com.luoguohua.finance.boot.system.service.FinanceUserDetailService;
import com.luoguohua.finance.common.constant.EndpointConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private FinanceWebLoginSuccessHandler successHandler;
    @Autowired
    private FinanceWebLoginFailureHandler failureHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Autowired
    private SystemProperties properties;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers()
                .antMatchers(EndpointConstant.OAUTH_ALL, EndpointConstant.LOGIN)
                .and()
                .authorizeRequests()
                .antMatchers(EndpointConstant.OAUTH_ALL).authenticated()
                .and()
                .formLogin()
                .loginPage(EndpointConstant.LOGIN)
                .loginProcessingUrl(EndpointConstant.LOGIN)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and().csrf().disable()
                .httpBasic().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(financeUserDetailService).passwordEncoder(passwordEncoder());
        super.configure(auth);
    }
}

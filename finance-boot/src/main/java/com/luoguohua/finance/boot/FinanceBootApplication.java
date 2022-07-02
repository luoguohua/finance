package com.luoguohua.finance.boot;

import com.luoguohua.finance.common.annotation.EnableAsyncExecPool;
import com.luoguohua.finance.common.annotation.EnableFinanceLettuceRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author luoguohua
 *
 * @EnableTransactionManagement 事务管理
 * @EnableFinanceLettuceRedis redis线程池注册
 * @EnableAsyncExecPool 开启异步线程池
 */
@SpringBootApplication
@MapperScan("com.luoguohua.finance.*.mapper")
@ComponentScan(basePackages = {"com.luoguohua.finance"})
@EnableFinanceLettuceRedis
@EnableTransactionManagement
@EnableAsyncExecPool
public class FinanceBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceBootApplication.class, args);
    }

}

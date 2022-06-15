package com.luoguohua.finance.boot;

import com.luoguohua.finance.common.annotation.EnableFinanceLettuceRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author luoguohua
 */
@SpringBootApplication
@MapperScan("com.luoguohua.finance.*.mapper")
@ComponentScan(basePackages = {"com.luoguohua.finance"})
@EnableFinanceLettuceRedis
@EnableTransactionManagement
public class FinanceBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceBootApplication.class, args);
    }

}

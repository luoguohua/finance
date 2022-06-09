package com.luoguohua.finance.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luoguohua
 */
@SpringBootApplication
@MapperScan("com.luoguohua.finance.*.mapper")
public class FinanceBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceBootApplication.class, args);
    }

}

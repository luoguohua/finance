package com.luoguohua.finance.common.exception;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 14:45
 * Content:
 */
public class FinanceException extends RuntimeException{
    private static final long serialVersionUID = -6916154462432027437L;

    public FinanceException(String message){
        super(message);
    }
}

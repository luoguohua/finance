package com.luoguohua.finance.common.exception;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/14 11:40
 * Content:
 */
public class ValidateCodeException extends Exception{
    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message){
        super(message);
    }
}

package com.luoguohua.finance.common.pojo.vo;

import java.util.HashMap;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:47
 * Content:
 */
public class FinanceResponse extends HashMap<String,Object> {

    public FinanceResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public FinanceResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public FinanceResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}

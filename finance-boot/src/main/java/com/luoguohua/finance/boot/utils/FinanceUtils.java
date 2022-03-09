package com.luoguohua.finance.boot.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 10:50
 * Content:
 */
@Slf4j
public class FinanceUtils {

    /**
     * 设置响应
     *
     * @param response    HttpServletResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param value       响应内容
     * @throws IOException IOException
     */
    public static void makeResponse(HttpServletResponse response, String contentType,
                                    int status, Object value) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JSONUtil.parseObj(value).toStringPretty().getBytes());
    }
}

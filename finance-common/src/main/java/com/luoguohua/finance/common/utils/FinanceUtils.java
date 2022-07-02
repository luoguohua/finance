package com.luoguohua.finance.common.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luoguohua.finance.common.constant.RegexpConstant;
import com.luoguohua.finance.common.pojo.dto.CurrentUser;
import com.luoguohua.finance.common.pojo.dto.FinanceUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/9 10:50
 * Content:
 */
@Slf4j
public class FinanceUtils {


    private static final String UN_KNOW = "unknown";

    public static String getDoubleColon(){
        return StringPool.COLON+StringPool.COLON;
    }


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

    /**
     * 封装前端分页表格所需数据
     *
     * @param pageInfo pageInfo
     * @return Map<String, Object>
     */
    public static Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", pageInfo.getRecords());
        data.put("total", pageInfo.getTotal());
        return data;
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式字符串
     * @param value 要匹配的字符串
     * @return 正则校验结果
     */
    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 获取在线用户信息
     *
     * @return CurrentUser 当前用户信息
     */
    public static CurrentUser getCurrentUser() {
        try {
            LinkedHashMap<String, Object> authenticationDetails = getAuthenticationDetails();
            Object principal = authenticationDetails.get("principal");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(mapper.writeValueAsString(principal), CurrentUser.class);
        } catch (Exception e) {
            log.error("获取当前用户信息失败", e);
            return null;
        }
    }

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求IP
     *
     * @return String IP
     */
    public static String getHttpServletRequestIpAddress() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取当前用户名称
     *
     * @return String 用户名
     */
    public static String getCurrentUsername() {
        Object principal = getOauth2Authentication().getPrincipal();
        if (principal instanceof FinanceUser) {
            return ((FinanceUser) principal).getUsername();
        }
        return (String) getOauth2Authentication().getPrincipal();
    }

    /**
     * 获取当前用户权限集
     *
     * @return Collection<GrantedAuthority>权限集
     */
    public static Collection<GrantedAuthority> getCurrentUserAuthority() {
        return getOauth2Authentication().getAuthorities();
    }

    private static OAuth2Authentication getOauth2Authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (OAuth2Authentication) authentication;
    }


    private static LinkedHashMap<String, Object> getAuthenticationDetails() {
        return (LinkedHashMap<String, Object>) getOauth2Authentication().getUserAuthentication().getDetails();
    }

    /**
     * 判断是否包含中文
     *
     * @param value 内容
     * @return 结果
     */
    public static boolean containChinese(String value) {
        if (StringUtils.isBlank(value)) {
            return Boolean.FALSE;
        }
        Matcher matcher = RegexpConstant.CHINESE.matcher(value);
        return matcher.find();
    }

    /**
     * 判断是否为 ajax请求
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    /**
     * 设置成功响应
     *
     * @param response HttpServletResponse
     * @param value    响应内容
     * @throws IOException IOException
     */
    public static void makeSuccessResponse(HttpServletResponse response, Object value) throws IOException {
        makeResponse(response, MediaType.APPLICATION_JSON_VALUE, HttpServletResponse.SC_OK, value);
    }

    /**
     * 设置失败响应
     *
     * @param response HttpServletResponse
     * @param value    响应内容
     * @throws IOException IOException
     */
    public static void makeFailureResponse(HttpServletResponse response, Object value) throws IOException {
        makeResponse(response, MediaType.APPLICATION_JSON_VALUE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, value);
    }

    /**
     * 设置JSON类型响应
     *
     * @param response HttpServletResponse
     * @param status   http状态码
     * @param value    响应内容
     * @throws IOException IOException
     */
    public static void makeJsonResponse(HttpServletResponse response, int status, Object value) throws IOException {
        makeResponse(response, MediaType.APPLICATION_JSON_VALUE, status, value);
    }
}

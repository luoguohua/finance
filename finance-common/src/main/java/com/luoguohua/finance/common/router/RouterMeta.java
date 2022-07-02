package com.luoguohua.finance.common.router;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 11:54
 * Content:
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta implements Serializable {

    private static final long serialVersionUID = 5499925008927195914L;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;

    private Boolean breadcrumb = true;
}

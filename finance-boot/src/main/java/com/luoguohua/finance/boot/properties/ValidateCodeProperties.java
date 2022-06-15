package com.luoguohua.finance.boot.properties;

import lombok.Data;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/14 11:30
 * Content:
 */
@Data
public class ValidateCodeProperties {

    /**
     * 是否启用（默认启用）
     */
    private Boolean enable = true;

    /**
     * 验证码有效时间，单位秒
     */
    private Long time = 120L;
    /**
     * 图片宽度，px
     */
    private Integer width = 130;
    /**
     * 图片高度，px
     */
    private Integer height = 48;
    /**
     * 验证码位数
     */
    private Integer length = 4;

}

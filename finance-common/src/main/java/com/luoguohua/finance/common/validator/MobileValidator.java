package com.luoguohua.finance.common.validator;

import cn.hutool.core.util.StrUtil;
import com.luoguohua.finance.common.annotation.IsMobile;
import com.luoguohua.finance.common.constant.RegexpConstant;
import com.luoguohua.finance.common.utils.FinanceUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/6/15 14:52
 * Content:
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StrUtil.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return FinanceUtils.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}

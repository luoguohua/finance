package com.luoguohua.finance.boot.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luoguohua.finance.common.utils.FinanceUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/7/6 15:22
 * Content:
 */
@Component
public class FinanceMetaObjectHandler implements MetaObjectHandler {



    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtil.date());
        this.strictInsertFill(metaObject, "createUser", Long.class, FinanceUtils.getCurrentUser().getUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtil.date());
        this.strictInsertFill(metaObject, "modifyUser", Long.class, FinanceUtils.getCurrentUser().getUserId());
    }
}

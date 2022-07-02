package com.luoguohua.finance.system.dto;


import com.luoguohua.finance.common.pojo.dto.Tree;
import com.luoguohua.finance.system.po.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MrBird
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<SysMenu> {

    private String path;
    private String component;
    private String perms;
    private String icon;
    private String type;
    private Integer orderNum;
}

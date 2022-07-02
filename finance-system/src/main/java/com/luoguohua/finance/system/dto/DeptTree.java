package com.luoguohua.finance.system.dto;


import com.luoguohua.finance.common.pojo.dto.Tree;
import com.luoguohua.finance.system.po.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MrBird
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends Tree<SysDept> {

    private Integer orderNum;
}

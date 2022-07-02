package com.luoguohua.finance.system.controller;

import cn.hutool.core.text.StrPool;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.system.po.SysDept;
import com.luoguohua.finance.system.service.IDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dept")
@RequiredArgsConstructor
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @GetMapping
    public FinanceResponse deptList(QueryRequest request, SysDept dept) {
        Map<String, Object> depts = this.deptService.findSysDepartments(request, dept);
        return new FinanceResponse().data(depts);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('dept:add')")
    public void addDept(@Valid SysDept dept) {
        this.deptService.createSysDept(dept);
    }

    @DeleteMapping("/{deptIds}")
    @PreAuthorize("hasAuthority('dept:delete')")
    public void deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        String[] ids = deptIds.split(StrPool.COMMA);
        this.deptService.deleteSysDepartments(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('dept:update')")
    public void updateDept(@Valid SysDept dept) {
        this.deptService.updateSysDept(dept);
    }

}

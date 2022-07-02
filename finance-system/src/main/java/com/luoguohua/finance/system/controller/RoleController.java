package com.luoguohua.finance.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.system.po.Role;
import com.luoguohua.finance.system.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private  IRoleService roleService;

    @GetMapping
    public FinanceResponse roleList(QueryRequest queryRequest, Role role) {
        Map<String, Object> dataTable = FinanceUtils.getDataTable(roleService.findRoles(role, queryRequest));
        return new FinanceResponse().data(dataTable);
    }

    @GetMapping("options")
    public FinanceResponse roles() {
        List<Role> allRoles = roleService.findAllRoles();
        return new FinanceResponse().data(allRoles);
    }

    @GetMapping("check/{roleName}")
    public boolean checkRoleName(@NotBlank(message = "{required}") @PathVariable String roleName) {
        Role result = this.roleService.findByName(roleName);
        return result == null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:add')")
    public void addRole(@Valid Role role) {
        this.roleService.createRole(role);
    }

    @DeleteMapping("/{roleIds}")
    @PreAuthorize("hasAuthority('role:delete')")
    public void deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        String[] ids = roleIds.split(StringPool.COMMA);
        this.roleService.deleteRoles(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('role:update')")
    public void updateRole(@Valid Role role) {
        this.roleService.updateRole(role);
    }

}

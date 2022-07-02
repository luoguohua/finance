package com.luoguohua.finance.system.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.system.po.SysDept;

import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
public interface IDeptService extends IService<SysDept> {

    /**
     * 获取部门信息
     *
     * @param request request
     * @param dept    dept
     * @return 部门信息
     */
    Map<String, Object> findSysDepartments(QueryRequest request, SysDept dept);

    /**
     * 获取部门列表
     *
     * @param dept    dept
     * @param request request
     * @return 部门列表
     */
    List<SysDept> findSysDepartments(SysDept dept, QueryRequest request);

    /**
     * 创建部门
     *
     * @param dept dept
     */
    void createSysDept(SysDept dept);

    /**
     * 更新部门
     *
     * @param dept dept
     */
    void updateSysDept(SysDept dept);

    /**
     * 删除部门
     *
     * @param deptIds 部门id数组
     */
    void deleteSysDepartments(String[] deptIds);
}

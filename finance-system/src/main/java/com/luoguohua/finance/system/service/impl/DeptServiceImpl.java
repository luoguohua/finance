package com.luoguohua.finance.system.service.impl;

import com.luoguohua.finance.common.utils.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoguohua.finance.common.constant.PageConstant;
import com.luoguohua.finance.common.pojo.dto.Tree;
import com.luoguohua.finance.common.pojo.vo.QueryRequest;
import com.luoguohua.finance.system.dto.DeptTree;
import com.luoguohua.finance.system.mapper.DeptMapper;
import com.luoguohua.finance.system.po.SysDept;
import com.luoguohua.finance.system.service.IDeptService;
import com.luoguohua.finance.system.service.IUserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author MrBird
 */
@Slf4j
@Service("deptService")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, SysDept> implements IDeptService {

    @Autowired
    private IUserDataPermissionService userDataPermissionService;

    @Override
    public Map<String, Object> findSysDepartments(QueryRequest request, SysDept dept) {
        Map<String, Object> result = new HashMap<>(4);
        try {
            List<SysDept> departments = findSysDepartments(dept, request);
            List<DeptTree> trees = new ArrayList<>();
            buildTrees(trees, departments);
            List<? extends Tree<?>> deptTree = TreeUtil.build(trees);

            result.put(PageConstant.ROWS, deptTree);
            result.put(PageConstant.TOTAL, departments.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put(PageConstant.ROWS, null);
            result.put(PageConstant.TOTAL, 0);
        }
        return result;
    }

    @Override
    public List<SysDept> findSysDepartments(SysDept dept, QueryRequest request) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().like(SysDept::getDeptName, dept.getDeptName());
        }
        if (StringUtils.isNotBlank(dept.getCreateTimeFrom()) && StringUtils.isNotBlank(dept.getCreateTimeTo())) {
            queryWrapper.lambda()
                    .ge(SysDept::getCreateTime, dept.getCreateTimeFrom())
                    .le(SysDept::getCreateTime, dept.getCreateTimeTo());
        }
        queryWrapper.lambda().orderByAsc(SysDept::getOrderNum);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSysDept(SysDept dept) {
        if (dept.getParentId() == null) {
            dept.setParentId(SysDept.TOP_DEPT_ID);
        }
        dept.setCreateTime(new Date());
        this.save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysDept(SysDept dept) {
        if (dept.getParentId() == null) {
            dept.setParentId(SysDept.TOP_DEPT_ID);
        }
        dept.setModifyTime(new Date());
        this.baseMapper.updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysDepartments(String[] deptIds) {
        this.delete(Arrays.asList(deptIds));
    }

    private void buildTrees(List<DeptTree> trees, List<SysDept> depts) {
        depts.forEach(dept -> {
            DeptTree tree = new DeptTree();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setLabel(dept.getDeptName());
            tree.setOrderNum(dept.getOrderNum());
            trees.add(tree);
        });
    }

    private void delete(List<String> deptIds) {
        removeByIds(deptIds);
        userDataPermissionService.deleteByDeptIds(deptIds);

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysDept::getParentId, deptIds);
        List<SysDept> depts = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(depts)) {
            List<String> deptIdList = new ArrayList<>();
            depts.forEach(d -> deptIdList.add(String.valueOf(d.getDeptId())));
            this.delete(deptIdList);
        }
    }

}

package com.xpand.system.modules.sys.dao;

import com.xpand.common.core.base.BaseDao;
import com.xpand.common.core.page.annotation.MyBatisDao;
import com.xpand.system.modules.sys.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成，请不要手动修改
 */
@MyBatisDao
public interface SysPermissionDao extends BaseDao<SysPermission> {
    List<SysPermission> findPermissionsByRoleIds(@Param(value = "roleIds") List<String> roleIds);
}
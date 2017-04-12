package com.xpand.system.modules.sys.dao;

import com.xpand.common.core.base.BaseDao;
import com.xpand.common.core.page.annotation.MyBatisDao;
import com.xpand.system.modules.sys.model.SysRole;
import com.xpand.system.modules.sys.model.SysUser;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成，请不要手动修改
 */
@MyBatisDao
public interface SysRoleDao extends BaseDao<SysRole> {
    List<SysRole> findRoleByUserId(String userId);

    List<String> findRoleIdsByUserId(String userId);
}
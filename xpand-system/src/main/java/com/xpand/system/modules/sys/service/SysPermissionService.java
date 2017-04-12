package com.xpand.system.modules.sys.service;

import com.xpand.common.core.base.BaseService;
import com.xpand.system.modules.sys.dao.SysPermissionDao;
import com.xpand.system.modules.sys.model.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 17-4-12.
 */
@Service
public class SysPermissionService extends BaseService<SysPermissionDao,SysPermission> {
    @Autowired
    private SysPermissionDao sysPermissionDao;

    public List<SysPermission> findPermissionsByRoleIds(List<String> roleIds) {
        return sysPermissionDao.findPermissionsByRoleIds(roleIds);
    }
}

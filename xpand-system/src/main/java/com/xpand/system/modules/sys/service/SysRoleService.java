package com.xpand.system.modules.sys.service;

import com.xpand.common.core.base.BaseService;
import com.xpand.system.modules.sys.dao.SysRoleDao;
import com.xpand.system.modules.sys.dao.SysUserDao;
import com.xpand.system.modules.sys.model.SysRole;
import com.xpand.system.modules.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 17-4-12.
 */
@Service
public class SysRoleService extends BaseService<SysUserDao,SysUser>{

    @Autowired
    private SysRoleDao sysRoleDao;

    public List<SysRole> findRoleByUserId(String userId) {
        List<SysRole> list=sysRoleDao.findRoleByUserId(userId);
        return list;
//        return list!=null && list.size()>0 ? list.get(0) : null;
    }

    public List<String> findRoleIdsByUserId(String userId) {
        return sysRoleDao.findRoleIdsByUserId(userId);
    }
}

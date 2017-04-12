package com.xpand.system.modules.sys.service;

import com.xpand.common.core.base.BaseService;
import com.xpand.system.modules.sys.dao.SysUserDao;
import com.xpand.system.modules.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 17-4-12.
 */
@Service
public class SysUserService extends BaseService<SysUserDao,SysUser>{

    @Autowired
    private SysUserDao sysUserDao;

    public SysUser findUser(SysUser sysUser) {
        List<SysUser> list=sysUserDao.findLoginUser(sysUser);
        return list!=null && list.size()>0 ? list.get(0) : null;
    }
}

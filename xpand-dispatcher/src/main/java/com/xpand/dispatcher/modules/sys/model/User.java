package com.xpand.dispatcher.modules.sys.model;

import com.xpand.common.core.base.BaseModel;

/**
 * Created by root on 17-4-10.
 */
public class User extends BaseModel {
    private String name;
    private boolean mobileLogin;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

package com.xpand.dispatcher.modules.sys.model;

import com.xpand.common.core.base.BaseModel;

public class Car extends BaseModel{
    private static final long serialVersionUID = 1L;
    String name;
    String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.xpand.dispatcher.modules.sys.dao;

import com.xpand.common.core.base.BaseDao;
import com.xpand.common.core.page.Page;
import com.xpand.common.core.page.annotation.MyBatisDao;
import com.xpand.dispatcher.modules.sys.model.Car;

import java.util.List;

/**
 * Created by root on 17-4-10.
 */
@MyBatisDao
public interface CarDao extends BaseDao<Car>{
    List<Car> getCar1(Car car, Page<Car> page);
    List<Car> getCar4(Page<Car> page,Car car);
    List<Car> getCar3(Car car,Page<Car> page,Car car1);
    List<Car> getCar2(Car car);
}

package com.xpand.dispatcher.modules.sys.service;

import com.xpand.common.core.base.BaseService;
import com.xpand.common.core.page.Page;
import com.xpand.dispatcher.modules.sys.dao.CarDao;
import com.xpand.dispatcher.modules.sys.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 17-4-10.
 */
@Service
public class CarService extends BaseService<CarDao,Car>{
    @Autowired
    CarDao carDao;
    public Page<Car> getCar(Page<Car> page, Car car){

        List<Car> list=carDao.getCar3(car,page,car);
        page.setRecords(list);
        return page;
    }

    public List<Car> getCar(Car car){

        List<Car> list=carDao.getCar2(car);
        return list;
    }
}

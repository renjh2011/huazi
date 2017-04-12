package com.xpand.system.modules.sys.web;

import com.xpand.common.core.base.ResponseObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 17-4-12.
 */
@Controller
public class TestController {

    @RequestMapping(value="/login")
    @ResponseBody
    public ResponseObject login(HttpServletRequest request, HttpServletResponse response){
        return ResponseObject.failure(1,"qing chongxin deng lu");

    }

}

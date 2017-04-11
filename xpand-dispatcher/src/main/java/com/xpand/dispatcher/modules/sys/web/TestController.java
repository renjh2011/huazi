package com.xpand.dispatcher.modules.sys.web;

import com.xpand.common.core.config.Global;
import com.xpand.common.core.config.Resources;
import com.xpand.common.core.exception.LoginException;
import com.xpand.common.core.page.Page;
import com.xpand.common.core.utils.JwtHelper;
import com.xpand.common.core.utils.RedisUtil;
import com.xpand.dispatcher.core.shiro.StatelessToken;
import com.xpand.dispatcher.modules.sys.model.Car;
import com.xpand.dispatcher.modules.sys.service.CarService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-4-7.
 */
@Controller
public class TestController {
    @Autowired
    private CarService carService;
    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Car> test(HttpServletRequest request, HttpServletResponse response, Car car){
        Car car1=new Car();
        car1.setName("1");
        Car car2=new Car();
        car2.setName("2");

        List list=new ArrayList();
        list.add(car1);
        list.add(car2);

        Map map=new HashMap();
        map.put("list",list);
        Page<Car> page = carService.getCar(new Page<Car>(request, response), car1);
//        BeanPropertyBindingResult
//        "org.springframework.validation.BindingResult.car" -> "org.springframework.validation.BeanPropertyBindingResult: 0 errors"
        return page;
    }

    @RequestMapping(value = "/list2")
    @ResponseBody
    public List<Car> test1(HttpServletRequest request, HttpServletResponse response, Car car){
        Car car1=new Car();
        car1.setName("1");
        Car car2=new Car();
        car2.setName("2");

        List list=new ArrayList();
        list.add(car1);
        list.add(car2);

        Map map=new HashMap();
        map.put("list",list);
        List<Car> listCar = carService.getCar(car1);
//        BeanPropertyBindingResult
//        "org.springframework.validation.BindingResult.car" -> "org.springframework.validation.BeanPropertyBindingResult: 0 errors"
        return listCar;
    }

    @RequestMapping("/loginTest")   /*等效于 @RequestMapping(value = "/login",method = RequestMethod.POST) */
    @ResponseBody
    public Object login(ModelMap modelMap,
			/*@ApiParam为swagger使用的参数注解*/
                        @RequestParam(value = "account", required = false) String account,
                        // 添加测试 请求头
                        @RequestHeader(value = "token", defaultValue = "") String  headerToken,
                        @RequestParam(value = "password", required = false) String password) {
        // 参数检
        //登录逻辑
		/*String encryptPassword = sysUserService.encryptPassword(password);*/
//		Map<String, Object> result = LoginHelper.login(account,aa,"returnMap");
//		if((Boolean)result.get("isAuthenticated")){
//		if (LoginHelper.login(account,aa)) {
        // 2016年08月09日  下午 4:59:30 测试数据code
        // 如果登陆成功 且是 pc 登陆 生成 token
//			String token = "";
//			// 如果是 app 登陆
//			if(Objects.equals("app", ApiConstants.TYPE_APP)){
//				// 根据用户 id 生成 token
//				token = StringUtils.produceAppToken(account);
//				// 存入 redis
//				token = redisService.putAccessToken(account, token);
//			}else { // 网页端接口
//				token = StringUtils.producePcToken(account);
//				token = redisService.putBuyerToken(account, token);
//			}

//			modelMap.addAttribute("id",WebUtil.getCurrentUser());
//			modelMap.addAttribute("sessionId",SecurityUtils.getSubject().getSession().getId().toString());
//			UsernamePasswordToken stoken = (UsernamePasswordToken)result.get("token");
//			modelMap.addAttribute("account",stoken.getUsername());
//			modelMap.addAttribute("passowrd",stoken.getPassword());
//			Session session = (Session) result.get("session");
//			modelMap.addAttribute("sessionTimeout",session.getTimeout());
//			modelMap.addAttribute("token",token);
//			modelMap.addAttribute("headerToken",headerToken);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("countSql", 0);
        params.put("enable", 1);
        params.put("account", account);
		/*PageInfo<SysUser> pageInfo = sysUserService.query(params);
		AccessToken accessTokenEntity = new AccessToken();*/
        String accessToken = "";
        if(account.equals("admin")){


            accessToken = JwtHelper.createJWT(account,
                    "1",
                    "admin",
                    Global.getConfig("sofn.api.clientId"),
                    Global.getConfig("sofn.api.name"),
                    Long.parseLong(Global.getConfig("sofn.api.expiresSecond")) * 1000,
                    Global.getConfig("sofn.api.base64Secret"));
            RedisUtil.set(account + "1"+"pc-token", accessToken);
            Map<String, Object> map = new HashMap<String, Object>();
//			map.put(ApiConstants.CODE, ApiMsgConstants.SUCCESS_CODE);
//			map.put(ApiConstants.MSG, ApiMsgConstants.SUCCESS_MSG);
            map.put("token",accessToken);
            StatelessToken statelessToken=new StatelessToken(accessToken);
            SecurityUtils.getSubject().login(statelessToken);
            return map;
        }else{
            throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
        }
    }
}

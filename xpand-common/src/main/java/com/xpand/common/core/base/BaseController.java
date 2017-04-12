/**
 * 
 */
package com.xpand.common.core.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器基类
 * 
 * @author sofn
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseController {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	/** 获取当前用户Id */

	/** 设置成功响应代码 */

	/** 设置成功响应代码 */

	/** 设置响应代码 */

	/** 设置响应代码 */

	/** 异常处理 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		response.getOutputStream().write(JSONObject.toJSON(ResponseObject.failure(ex.getMessage())).toString().getBytes());
	}

	protected ResponseObject success() {
		return  ResponseObject.success();
	}

	protected <T> ResponseObject success(T data) {
		return  ResponseObject.success(data);
	}


	protected ResponseObject failure(String msg) {
		return  ResponseObject.failure(msg);
	}

	protected ResponseObject failure(int code, String msg) {
		return  ResponseObject.failure(code,msg);
	}
}

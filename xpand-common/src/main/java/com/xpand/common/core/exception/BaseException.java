/**
 * 
 */
package com.xpand.common.core.exception;

import com.xpand.common.core.base.ResponseObject;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {
	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}


	public ResponseObject handler(ResponseObject responseObject) {
		if (StringUtils.isNotBlank(getMessage())) {
			responseObject= ResponseObject.failure(getMessage());
		} else {
			responseObject= ResponseObject.failure();
		}
		return responseObject;
	}

	protected abstract String getCode();
}

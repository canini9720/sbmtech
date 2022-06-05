package com.sbmtech.common.util;

import java.util.Map;

import com.sbmtech.common.constant.CommonConstants;

public class CommonUtil {

	public static String getDefaultIfNull(String paramVal, String defaultVal) {

		if ((paramVal != null) && (paramVal.trim().length() > 0) && !(paramVal.equalsIgnoreCase("null"))) {

			return paramVal;

		} else {

			return defaultVal;
		}
	}

	public static String getSuccessOrFailureMessageWithId(int result) {

		if (result == CommonConstants.SUCCESS_CODE) {

			return CommonConstants.SUCCESS_DESC;

		} else if (result == CommonConstants.PENDING_CODE) {

			return CommonConstants.PENDING_DESC;

		} else {

			return CommonConstants.FAILURE_DESC;
		}
	}
	public static String generateStackTraceFromDTO(Object dtoObj,String serviceMethodName)  {
	     
		try
		{
			Map<String,String> map = org.apache.commons.beanutils.BeanUtils.describe(dtoObj);
			return map.toString();
		}
		catch(Exception ex)
		{
			
			return null;
		}

	}
}

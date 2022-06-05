package com.sbmtech.exception;

import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionBusinessConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.common.util.ValidationUtil;


public class ExceptionUtil {
	public static final int EXCEPTION_VALIDATION = 1;
	public static final int EXCEPTION_BUSINESS = 2;
	public static final int EXCEPTION_CUSTOM = 3;
	public static final int EXCEPTION_CUSTOM_CODE = 1000;
	public static final String PROPS_VALIDATIONS="exception_validations";
	public static final String PROPS_BUSINESS="exception_business";
	public static final String SERVICES_EXCEPTION = "DPSE";
	
	private static final Logger logger = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	private static final Logger loggerBre = Logger.getLogger(CommonConstants.LOGGER_SERVICES_BRE);
	

	static ResourceBundle resource_validation = null;
	static ResourceBundle resource_business = null;
	
	public static void throwException(String message, int excType) throws BusinessException{
		
		try{
			
			throwExceptionFinal("", message, excType);
			
		}catch(BusinessException ex){
			loggerBre.info("ExceptionUtil-->throwException", ex);
			throw ex;
		}catch(ValidationException ex){
			loggerInfo.info("ExceptionUtil-->throwException", ex);
			throw ex;
		}catch(RuntimeException ex){
			logger.error("ExceptionUtil-->throwException", ex);
			throw ex;
		}
	}
	
	public static void throwExceptionFinal(String fieldName, String message, int excType){
		
		if(excType == EXCEPTION_CUSTOM){
			
			message = EXCEPTION_CUSTOM_CODE+" - "+message;
			
		}else{
			
			String errorMessage = getErrorMessageFromProps(message, excType);
			
			if(fieldName != null && !fieldName.isEmpty()){
				errorMessage = errorMessage.replace("{fieldName}", fieldName);
			}
			
			fieldName = null;
				
			message += " - "+errorMessage;
		}
		
		message += " - "+SERVICES_EXCEPTION;
		
		if(excType == EXCEPTION_VALIDATION){
						
			throw new ValidationException(message);
			
		}else if(excType == EXCEPTION_BUSINESS){
			
			throw new BusinessException(message);
			
		}else if(excType == EXCEPTION_CUSTOM){
			
			throw new RuntimeException(message);
		}
		
	}
	
	public static void throwInvalidEmailValException(String email, boolean isRequired) throws Exception{
		boolean isValid = true;
		
		if(isRequired){
			
			if(email == null || email.isEmpty()){
			
				ExceptionUtil.throwNullOrEmptyValidationException("Email", email, true);
			}
			
			isValid = ValidationUtil.validateEmail(email);
			
		}else{
			
			if(email != null && !email.isEmpty()){
				
				isValid = ValidationUtil.validateEmail(email);
			}
			
		}
		
		if(!isValid){
			
			throwExceptionFinal(null, ExceptionValidationsConstants.INVALID_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		
	}
	
	public static void throwInvalidPhoneNoException(Long phoneNo, boolean isRequired, String fieldName) throws Exception{
		boolean isValid = true;
		
		String phoneNoStr = String.valueOf(phoneNo);
		
		if(isRequired){
			
			if(phoneNoStr == null || phoneNoStr.isEmpty()){
				
				ExceptionUtil.throwNullOrEmptyValidationException(fieldName, phoneNoStr, true);
			}
			
			isValid = ValidationUtil.validatePhoneNo(phoneNoStr);
			
		}else{
			
			if(phoneNoStr != null && !phoneNoStr.isEmpty() && !phoneNoStr.equals("null") && !phoneNoStr.equals("0")){
				
				isValid = ValidationUtil.validatePhoneNo(phoneNoStr);
			}
			
		}
		
		if(!isValid){
			throwExceptionFinal(fieldName, ExceptionValidationsConstants.INVALID_PHONE, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		
	}
	
	public static void throwInvalidTimeException(String time, boolean isRequired) throws Exception{
		boolean isValid = true;
		
		if(isRequired){
			
			if(time == null || time.isEmpty()){
			
				ExceptionUtil.throwNullOrEmptyValidationException("Time", time, true);
			}
			
			isValid = ValidationUtil.validateTimeFormat_24HRS(time);
			
		}else{
			
			if(time != null && !time.isEmpty()){
				
				isValid = ValidationUtil.validateTimeFormat_24HRS(time);
			}
			
		}
		
		if(!isValid){
			
			throwExceptionFinal(null, ExceptionValidationsConstants.INVALID_TIME_FORMAT, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		
	}
	
	public static void throwInvalidDateException(String date, String fieldName, boolean isRequired) throws Exception{
		
		boolean isValid = true;
		
		if(isRequired){
			
			if(date == null || date.isEmpty()){
			
				ExceptionUtil.throwNullOrEmptyValidationException(fieldName, date, true);
			}
			
			isValid = ValidationUtil.validateDateFormat(date, CommonConstants.DATE_ddMMyyyy);
			
			
		}else{
			
			if(date != null && !date.isEmpty()){
				
				isValid = ValidationUtil.validateDateFormat(date, CommonConstants.DATE_ddMMyyyy);
			}
			
		}
		
		if(!isValid){
			
			throwExceptionFinal(null, ExceptionValidationsConstants.INVALID_DATE_FORMAT, ExceptionUtil.EXCEPTION_VALIDATION);
		}
	}
	
	public static void throwInvalidDateException(String date, String pattern, String fieldName, boolean isRequired) throws Exception{
		
		boolean isValid = true;
		
		if(isRequired){
			
			if(date == null || date.isEmpty()){
			
				ExceptionUtil.throwNullOrEmptyValidationException(fieldName, date, true);
			}
			
			isValid = ValidationUtil.validateDateFormat(date, pattern);
			
			
		}else{
			
			if(date != null && !date.isEmpty()){
				
				isValid = ValidationUtil.validateDateFormat(date, pattern);
			}
			
		}
		
		if(!isValid){
			
			throwExceptionFinal(null, ExceptionValidationsConstants.INVALID_DATE_FORMAT, ExceptionUtil.EXCEPTION_VALIDATION);
		}
	}
	
	public static void throwNullOrEmptyValidationException(String fieldName, Object value, boolean validateZero) throws Exception{
		boolean isValid = false;
		
		if(value != null && value.getClass().isArray()){
			
			isValid = ValidationUtil.isArrayNotNullAndNotEmpty(value);
			
		}else{
			
			isValid = ValidationUtil.isStringValueNotNullAndNotEmpty(value);
		}
		
		if(validateZero && (value != null && !value.toString().isEmpty())){
			
			isValid = !ValidationUtil.isStringValueZero(value.toString());
		}
		
		if(!validateZero && !fieldName.isEmpty()){
			
			if(value != null){
			
				isValid = ValidationUtil.validateNullString(value.toString());
			}
		}
		
		if(!isValid){
			
			throwExceptionFinal(fieldName, ExceptionValidationsConstants.REQUIRED_FIELD_EMPTY, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		
	}
	
	public static void throwSizeExceedsException(String fieldName, String field, int fieldSizeExpected)throws Exception
	{
		if(field.length()>fieldSizeExpected)
		{
			String errorMessage = getErrorMessageFromProps(ExceptionValidationsConstants.MAX_FIELD_LENGTH_CHECK, ExceptionUtil.EXCEPTION_VALIDATION);
			errorMessage = errorMessage.replace("{fieldName}", fieldName);
			errorMessage = errorMessage.replace("{size}", fieldSizeExpected+"");
			String message = ExceptionValidationsConstants.MAX_FIELD_LENGTH_CHECK; 
			message = message+=" - "+errorMessage;
			message += " - "+SERVICES_EXCEPTION;
			throw new ValidationException(message);
		}
			
	}
	
	public static void throwNoDataExceptionFromJPA(PersistenceException ex, String excCode) throws Exception{
		if(ex.getMessage().contains(CommonConstants.EXC_NO_DATA_FOUND_JPA_ERROR)){
			
			throwExceptionFinal(null, excCode, ExceptionUtil.EXCEPTION_BUSINESS);
			
		}else{
			
			throw ex;
		}
	}
	
	public static void throwNoDataException() throws BusinessException{
		
		ExceptionUtil.throwException(ExceptionBusinessConstants.NO_DATA_FOUND, ExceptionUtil.EXCEPTION_BUSINESS);
	}
	
public static String getFinalExcCause(PersistenceException ex){
		
		Throwable cause = ex.getCause();
		String message = cause.getMessage();
		
		while(cause != null){
			
			cause = cause.getCause();
			
			if(cause != null){
				
				message = cause.getMessage();
			}
		}
		
		return message;
	}
	
	public static String getErrorMessageFromProps(String errorCode, int excType){
		String errorMessage = "";
		
		if(resource_validation == null){
			
			resource_validation = ResourceBundle.getBundle(PROPS_VALIDATIONS);
		}
		
		if(resource_business == null){
			
			resource_business = ResourceBundle.getBundle(PROPS_BUSINESS);
		}
		
		if(excType == EXCEPTION_VALIDATION){
			
			errorMessage = resource_validation.getString(errorCode);
			
		}else if(excType == EXCEPTION_BUSINESS){
			
			errorMessage = resource_business.getString(errorCode);
		}
		
		return errorMessage;
	}
	
	public  static void logDTOStackTrace(String endpointServiceName, Exception ex, Object dtoObj) {
		
		if(ex instanceof BusinessException){
			
			loggerInfo.info(CommonUtil.generateStackTraceFromDTO(dtoObj, endpointServiceName), ex);	//throw ex;
			
		}else if(ex instanceof ValidationException){
			
			loggerInfo.info(CommonUtil.generateStackTraceFromDTO(dtoObj, endpointServiceName), ex);	//throw ex;
			
		}else{
			
			logger.error(CommonUtil.generateStackTraceFromDTO(dtoObj, endpointServiceName), ex);	//throw ex;
		}
	}
	
	public  static void logParamStackTrace(String endpointServiceName, Exception ex, String param) {
		
		if(ex instanceof BusinessException){
			
			loggerInfo.info(param,ex);
			
		}else if(ex instanceof ValidationException){
			
			loggerInfo.info(param,ex);
			
		}else{
			
			logger.error(param,ex);
		}
	}
	
}

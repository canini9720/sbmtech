package com.sbmtech.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionBusinessConstants;

@RestControllerAdvice
public class RestServicesExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger logger = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	private static final Logger loggerBRE = Logger.getLogger(CommonConstants.LOGGER_SERVICES_BRE);
	
	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
			Object body, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		loggerInfo.info("<< handleExceptionInternal >>");
		
		
		printErrorLogsToLogger(ex, request);
		
		 
		String errorMsg = getApplicationErrorMessage(ex);
		JSONParser parser = new JSONParser();
		JSONObject json=null;
		try {
			json = (JSONObject) parser.parse(errorMsg);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		ResponseEntity<Object> responseEntity =  super.handleExceptionInternal(ex, json, headers, getHttpStatus(ex), request);
		
		return responseEntity;
	}
	
	@ExceptionHandler(value = { IntrusionException.class })
	@ResponseBody
	protected ResponseEntity<Object> handleIntrusionException(Exception ex, WebRequest request) {
		
		loggerInfo.info("<< handleIntrusionException >>");
		
		
		printErrorLogsToLogger(ex, request);
		
		
		String errorMsg = getApplicationErrorMessage(ex);
		JSONParser parser = new JSONParser();
		JSONObject json=null;
		try {
			json = (JSONObject) parser.parse(errorMsg);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<Object>(json, getHttpStatus(ex));
	 }
	
	@ExceptionHandler(value = { BusinessException.class, ValidationException.class })
	@ResponseBody
	protected ResponseEntity<Object> handleValidationAndBusinessExceptions(Exception ex, WebRequest request) {
		
		loggerInfo.info("<< handleValidationAndBusinessExceptions >>");
		
		
		printErrorLogsToLogger(ex, request);
		
		
		String errorMsg = getApplicationErrorMessage(ex);
		JSONParser parser = new JSONParser();
		JSONObject json=null;
		try {
			json = (JSONObject) parser.parse(errorMsg);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<Object>(json, getHttpStatus(ex));
	 }
	
	@ExceptionHandler(value = { Exception.class })
	@ResponseBody
	protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		
		loggerInfo.info("<< handleAllExceptions >>");
		JSONParser parser = new JSONParser();
		JSONObject json=null;
		
		printErrorLogsToLogger(ex, request);
		if(ex instanceof BadCredentialsException){
			json=new JSONObject();
			json.put("responseCode", "0");
			json.put("responseDesc", "Bad Credential");
			return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
		}else if(ex instanceof AccessDeniedException){
			json=new JSONObject();
			json.put("responseCode", "0");
			json.put("responseDesc", "Access Denied");
			return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
		}else if(ex instanceof DisabledException){
			json=new JSONObject();
			json.put("responseCode", "0");
			json.put("responseDesc", "User Account is Disabled");
			return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
		}
		
		String errorMsg = getApplicationErrorMessage(ex);
		
		try {
			json = (JSONObject) parser.parse(errorMsg);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return new ResponseEntity<Object>(json, getHttpStatus(ex));
	 }
	
	private String getApplicationErrorMessage(Exception ex){
		JSONObject responseObject = new JSONObject();
		Gson gson = new Gson();
		
		String errorCode = CommonConstants.STR_ZERO;
		String errorMsg  = CommonConstants.EXC_SERVER_ERROR;//ex.getMessage();
		
		if(ex instanceof ValidationException ||
				ex instanceof BusinessException){
			
			String errorDtls[] = getErrorCodeAndDesc(ex.getMessage());
			
			errorCode = errorDtls[0].trim();
			errorMsg = errorDtls[1].trim();
						
		}else if(ex instanceof MissingServletRequestParameterException){
			errorMsg = ex.getMessage();
			
			errorMsg = errorMsg.replace("\'", "");
		}/*
		else if(ex instanceof BadCredentialsException){
			errorMsg = ex.getMessage();
			
			errorMsg = errorMsg.replace("\'", "");
		}else if(ex instanceof AccessDeniedException){
			errorMsg = ex.getMessage();
			
			errorMsg = errorMsg.replace("\'", "");
		}
		else if(ex instanceof DisabledException){
			errorMsg = ex.getMessage();
			
			errorMsg = errorMsg.replace("\'", "");
		}*/else if(ex instanceof ServletRequestBindingException){
			errorMsg = ex.getMessage();
			
			errorMsg = errorMsg.replace("\'", "");
		}else if(ex instanceof SQLException){
			
			if(((SQLException) ex).getErrorCode() == CommonConstants.EXC_NO_DATA_FOUND){
				
				errorCode = ExceptionBusinessConstants.NO_DATA_FOUND;
				errorMsg = ExceptionUtil.getErrorMessageFromProps(ExceptionBusinessConstants.NO_DATA_FOUND, ExceptionUtil.EXCEPTION_BUSINESS);
			}
			
		}else if( ex instanceof NoResultException){
			
			errorCode = ExceptionBusinessConstants.NO_DATA_FOUND;
			errorMsg = ExceptionUtil.getErrorMessageFromProps(ExceptionBusinessConstants.NO_DATA_FOUND, ExceptionUtil.EXCEPTION_BUSINESS);
		}
		
		responseObject.put(CommonConstants.RESPONSE_DESC, errorMsg);
		responseObject.put(CommonConstants.RESPONSE_CODE, errorCode);
		return gson.toJson(responseObject);
	}
	
	private HttpStatus getHttpStatus(Exception ex){
		
		if(ex instanceof ValidationException || ex instanceof BusinessException){
			
			return HttpStatus.OK;
			
		}else{
			
			return HttpStatus.OK;
		}
	}
	
	private String[] getErrorCodeAndDesc(String message){
		String errorDtls[] = new String[2];
		
		errorDtls = message.split("-");
		
		return errorDtls;
	}
	
	private String printRequestParams(WebRequest request){
		
		Map<String, String[]> requestMap = new HashMap<>(request.getParameterMap());
		
		for (Iterator<String> iterator = request.getHeaderNames(); iterator.hasNext();) {
			
			String headerName = iterator.next();
			
			requestMap.put(headerName, request.getHeaderValues(headerName));
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<<< REQUEST - START >>> ");
		
		if(requestMap != null){
			
			Set<Entry<String, String[]>> set = requestMap.entrySet();
			
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();
				
				sb.append(entry.getKey());
				sb.append(" : ");
				sb.append(entry.getValue()[0]);
				sb.append(", ");
			}
		}
		
		sb.append("<<< REQUEST - END >>>");
		
		return sb.toString();
	}
	
	private void printErrorLogsToLogger(Exception ex, WebRequest request){
		
		if(ex instanceof ValidationException ||
				ex instanceof BusinessException){
			
			loggerBRE.info(printRequestParams(request), ex);
			
		}else{
			
			logger.error(printRequestParams(request), ex);
		}
	}
	
}


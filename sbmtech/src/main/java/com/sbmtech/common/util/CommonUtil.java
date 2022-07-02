package com.sbmtech.common.util;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.exception.ExceptionUtil;

public class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	 public static String getTextValue(Element ele, String tagName) {
	        String textVal = null;
	        
	        NodeList nl = null;
	        
	        if (ele.getElementsByTagNameNS("*",tagName) != null) {
	        	
	            nl = ele.getElementsByTagNameNS("*",tagName);
	            
	            if (nl != null && nl.getLength() > 0) {
	            	
	                Element el = (Element)nl.item(0);
	                
	                if (el.getFirstChild() != null){
	                	
	                    textVal = el.getFirstChild().getNodeValue();
	                    
	                }
	                
	            }
	        }
	        return textVal;
	    }
	    
	    public static String getStringValue(String fieldName) {
			String result = "";
	    	if(fieldName != null && !fieldName.equalsIgnoreCase("-1")){
	    		return fieldName;
	    	}
	    	return result;
	    }
	    public static String getFormattedDate(Object dateToFormat) {
	    	
	        String strDate = null;
	        
	        try {
	        	
	        	if(dateToFormat != null){
	        		
		            SimpleDateFormat format2 = new SimpleDateFormat(CommonConstants.DATE_yyyy_MM_dd,Locale.ENGLISH);
		            SimpleDateFormat format1 = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy,Locale.ENGLISH);
		            
		            strDate = format1.format(format2.parse(dateToFormat.toString()));
	        	}
	            
	        } catch (Exception e) {
	        	
	            
	            
	        }
	        
	        return strDate;
	    }
	    
	    public static String getFormattedDateByPattern(Object dateToFormat, String fromPattern, String toPattern) {
	    	
	        String strDate = null;
	        
	        try {
	        	
	        	if(dateToFormat != null){
	        		
		            SimpleDateFormat format1 = new SimpleDateFormat(toPattern,Locale.ENGLISH);
		            SimpleDateFormat format2 = new SimpleDateFormat(fromPattern,Locale.ENGLISH);
		            
		            strDate = format1.format(format2.parse(dateToFormat.toString()));
	        	}
	            
	        } catch (Exception e) {
	        	
	            
	            
	        }
	        
	        return strDate;
	    }
	    
	    /**
	     * change date format base on the given pattern and date 
	     * paramter
	     * 
	     * @param dateToFormat
	     * @param pattern
	     * @return
	     */
	 public static String getFormattedDatePattern(Object dateToFormat,String pattern) {
	    	
	        String strDate = null;
	        
	        try {
	        	
	        	if(dateToFormat != null){
	        		
	        		if(pattern.equalsIgnoreCase(CommonConstants.DATE_yyyy_MM_dd)){
	        			SimpleDateFormat format2 = new SimpleDateFormat(CommonConstants.DATE_yyyy_MM_dd,Locale.ENGLISH);
	     	            SimpleDateFormat format1 = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy,Locale.ENGLISH);
	     	            strDate = format1.format(format2.parse(dateToFormat.toString()));
	        		}if(pattern.equalsIgnoreCase(CommonConstants.DATE_ddMMyyyy)){
	     	            SimpleDateFormat format1 = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy,Locale.ENGLISH);
	        			SimpleDateFormat format2 = new SimpleDateFormat(CommonConstants.DATE_yyyy_MM_dd,Locale.ENGLISH);
	     	            strDate = format1.format(format2.parse(dateToFormat.toString()));
	        		}if(pattern.equalsIgnoreCase(CommonConstants.DATE_ddMMyyyy_HH_MM_SS)){
	        			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
					    strDate = sdf.format(dateToFormat);	
					   
	        		}if(pattern.equalsIgnoreCase(CommonConstants.DATEyyyyMMdd)){
	        			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATEyyyyMMdd);
					    strDate = sdf.format(dateToFormat);	
					   
	        		}if(pattern.equalsIgnoreCase(CommonConstants.DATE_yyyyMMddHHmmss)){
	        			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_yyyyMMddHHmmss);
					    strDate = sdf.format(dateToFormat);	
					   
	        		}if(pattern.equalsIgnoreCase(CommonConstants.DATE_ddMMyyyy+" "+CommonConstants.DATE_HHMMSS)){
	        			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy+" "+CommonConstants.DATE_HHMMSS);
					    strDate = sdf.format(dateToFormat);	
					   
	        		}if(pattern.equalsIgnoreCase(CommonConstants.DATE_MMddyyyy_HH_MM)){
	        			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_MMddyyyy_HH_MM);
					    strDate = sdf.format(dateToFormat);	
	        		}
	        		if(pattern.equalsIgnoreCase(CommonConstants.DATE_ddMMyyyy_HH_MM_SS_SSS)){
	        			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy_HH_MM_SS_SSS);
					    strDate = sdf.format(dateToFormat);	
	        		}
	        		if(pattern.equalsIgnoreCase(CommonConstants.DATE_ddMMyyyyHHmmss)){
	        			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyyHHmmss);
					    strDate = sdf.format(dateToFormat);	
	        		}
	        		if(pattern.equalsIgnoreCase(CommonConstants.DATE_dd_MMM_yyyy)){
	        			SimpleDateFormat format2 = new SimpleDateFormat(CommonConstants.DATE_dd_MMM_yyyy,Locale.ENGLISH);
	     	            SimpleDateFormat format1 = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy,Locale.ENGLISH);
	     	            strDate = format1.format(format2.parse(dateToFormat.toString()));
	        		}
	        		
		           
	        	}
	            
	        } catch (Exception e) {
	        	
	            
	            
	        }
	        
	        return strDate;
	    }
	    
	    
	  //to get the system date in specified format 
	  	public static String getSystemDateFromFormat(String format){
	  		
			SimpleDateFormat formatter = null;
			long sysdate = System.currentTimeMillis();
			Date sys_date = new Date(sysdate);
			String system_date ="";
			if(format != null){
				
				formatter = new SimpleDateFormat(format);
				system_date = formatter.format(sys_date);
				
			}else{
				
				return "";
			}
	   
			return system_date;
	  	}
	  	
	  	public static Date getCurrentDate(int daysToAdd){
	  		
	  		Calendar calNow = Calendar.getInstance();
	  		
	  		calNow.add(Calendar.DAY_OF_MONTH, daysToAdd);
	  		
	  		return calNow.getTime();
	  	}
	  	
	  	  public static Integer getIntegerFromString(String text){
	      	Integer intText = null;
	      	if(text != null){
	      		intText=Integer.parseInt(text);
	      	}
	      	return intText;
	      	
	      }
	  	  
	    
	    /**
	     * to get Date Object of given String date with given pattern
	     * */
	    public static Date getDatefromString(String paramVal,String datePattern){
	    	
	    	if(paramVal != null && !paramVal.isEmpty()){
	    	
		        SimpleDateFormat sdf = new SimpleDateFormat(datePattern,Locale.ENGLISH);
		        
		        try{
		        	
		            if(paramVal != null && paramVal.trim().length() > 0 && !(paramVal.equalsIgnoreCase("null"))){
		            	
		                return sdf.parse(paramVal);
		                
		            }else{
		            	
		                return null;
		                
		            }
		        }
		        catch(Exception e){
		            return null;
		        }
	    	}
	    	
	    	return null;
	    }
	    
	    public static String formatSysDate(String dateStr) throws ParseException{
	    	
	    	if(dateStr != null && !dateStr.isEmpty()){
	    	
		    	SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy",Locale.ENGLISH);
		    	
		    	Date date = (Date)formatter.parse(dateStr);
		    	
		    	return getStringDatefromDate(date);
		    	
	    	}else{
	    		
	    		return dateStr;
	    	}
	    }
	    
	    public static String getStringDatefromDate(Date date){
	    	String datePattern = "dd/MM/yyyy";
	    	
	        SimpleDateFormat sdf = new SimpleDateFormat(datePattern,Locale.ENGLISH);
	        
	        try{
	        	
	            if(date != null && !(date.toString().equalsIgnoreCase("null"))){
	            	
	                return sdf.format(date);
	                
	            }else{
	            	
	                return null;
	                
	            }
	        }
	        catch(Exception e){
	            return null;
	        }
	    }
	    
	    public static String getStringTimestampfromDate(Date date){
	    	String datePattern = "dd/MM/yyyy HH:mm:ss";
	    	
	        SimpleDateFormat sdf = new SimpleDateFormat(datePattern,Locale.ENGLISH);
	        
	        try{
	        	
	            if(date != null && !(date.toString().equalsIgnoreCase("null"))){
	            	
	                return sdf.format(date);
	                
	            }else{
	            	
	                return null;
	                
	            }
	        }
	        catch(Exception e){
	            return null;
	        }
	    }
	    
	    
	    /**
	     * to get string value 0 and 1 for boolean flags
	     * */
	    public static String getIntValuesFromBoolean(String booleanValue){
	    	
	        if((booleanValue != null) && (booleanValue.trim().length() > 0) && (booleanValue.equalsIgnoreCase("false"))){
	        	
	            return "0";
	            
	        }else{
	        	
	            return "1";
	        }
	    }
	    
	    /**
	     * to compare dates and get result in Integer as 0(Date1 > = Date2) or 1 (Date1 < Date2)
	     * @throws ParseException 
	     * */
	    public static int compareDates(String endDate, String startDate) throws ParseException
	    {

	    try{		
	        int result = 0;
	        
	        SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy);
	        
	        Date date1 = formatter.parse(endDate);
	        Date date2 = formatter.parse(startDate);
	        result = date1.compareTo(date2);
	        
	        if(result < 0)
	            return 1;
	        
	    }catch(Exception e){
	    }
	        
	        return 0;
	    }
	    
	    private static Calendar getCalendarInstanceWithoutTime(){
	    	
	    	Calendar calNow = Calendar.getInstance();
	    	
	    	calNow.set(Calendar.HOUR, 0);
	    	calNow.set(Calendar.HOUR_OF_DAY, 0);
	    	calNow.set(Calendar.MINUTE, 0);
	    	calNow.set(Calendar.SECOND, 0);
	    	calNow.set(Calendar.MILLISECOND, 0);
	    	
	    	return calNow;
	    	
	    }
	    
	    public static boolean isExpiredWithTime(Date dateTime){
	    	
	    	boolean isExpired = false;
	    	
	    	if(dateTime != null ){
	    	
		    	Calendar calGiven = Calendar.getInstance();
		    	Calendar calNow = Calendar.getInstance();
		    	
		    	calGiven.setTime(dateTime);
		    	calGiven.add(Calendar.MINUTE, 15);
		    	
		    	if(calGiven.before(calNow)){
		    		
		    		isExpired = true;
		    	}
		    	
	    	}else{
	    		
	    		isExpired = true;
	    	}
	    	
	    	return isExpired;
	    }
	    
	    public static boolean isExpired(String date, String datePattern) throws ParseException{
	    	boolean isExpired = false;
	    	
	    	if(date != null && !date.isEmpty() && !date.equalsIgnoreCase("null")){
	    	
		    	Calendar calNow = getCalendarInstanceWithoutTime();
		    	Calendar calGiven = getCalendarInstanceWithoutTime();
		    	
		    	SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		    	
		    	calGiven.setTime(formatter.parse(date));
		    	
		    	if(calGiven.before(calNow)){
		    		
		    		isExpired = true;
		    	}
	    	}else{
	    		
	    		isExpired = true;
	    	}
	    	
	    	return isExpired;
	    }
	    
	    /**
	     * to set response code and exception into reference object
	     * */
	    
	    public static JSONObject setExceptionAndStatus(JSONObject referenceObject, Exception e){
	    	
	    	referenceObject.put(CommonConstants.RESPONSE_CODE,CommonConstants.STR_ZERO);
	        referenceObject.put(CommonConstants.RESPONSE_DESC,e.getMessage());
	        
	        return referenceObject;
	    }
	    
	    public static Long getLongValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		try{
	    		
	    			return Long.parseLong(objVal.toString());
	    			
	    		}catch(NumberFormatException e){
	    			
	    			return 0L;
	    		}
	    		
	    	}else{
	    		
	    		return 0L;
	    	}
	    	
	    }
	    
	    public static Double getDoubleValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		try{
	    		
	    			return Double.parseDouble(objVal.toString());
	    		
	    		}catch(NumberFormatException e){
	    			
	    			return 0.0;
	    		}
	    		
	    	}else{
	    		
	    		return 0.0;
	    	}
	    	
	    }
	    
	    public static String getStringValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		return objVal.toString();
	    		
	    	}else{
	    		
	    		return "";
	    	}
	    	
	    }
	    
	    public static BigInteger getBigIntValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		try{
	    		
	    			return BigInteger.valueOf(CommonUtil.getLongValofObject(objVal));
	    			
	    		}catch(NumberFormatException e){
	    			
	    			return BigInteger.valueOf(0);
	    		}
	    		
	    	}else{
	    		
	    		return BigInteger.valueOf(0);
	    	}
	    	
	    }
	    
	    public static Integer getIntValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		try{
	    		
	    			return Integer.parseInt(objVal.toString());
	    			
	    		}catch(NumberFormatException e){
	    			
	    			return 0;
	    		}
	    		
	    	}else{
	    		
	    		return 0;
	    	}
	    	
	    }
	    
	    public static boolean getBooleanValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		try{
	    		
	    			return (Integer.parseInt(objVal.toString()) == 1 ?true:false);
	    			
	    		}catch(NumberFormatException e){
	    			
	    			return false;
	    		}	
	    		
	    	}else{
	    		
	    		return false;
	    	}
	    	
	    }
	    
	public static Float getFloatValofObject(Object objVal){
	    	
	    	if(objVal != null){
	    		
	    		try{
	    		
	    			return Float.parseFloat(objVal.toString());
	    		
	    		}catch(NumberFormatException e){
	    			
	    			return 0.0F;
	    		}
	    		
	    	}else{
	    		
	    		return 0.0F;
	    	}
	    	
	    }
	    
	    public static boolean checkDateWithGivenDays(String startDateStr, String endDateStr, int days) throws ParseException{
	    	
	    	 SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_ddMMyyyy);
	         Date endDate = formatter.parse(endDateStr);
	         
	         Calendar currentDate = getCalendarInstanceWithoutTime();
	         
	         if(startDateStr != null && !startDateStr.isEmpty()){
	        	 
	             Date startDate = formatter.parse(startDateStr);
	        	 
	        	 currentDate.setTimeInMillis(startDate.getTime());
	         }
	         
	         currentDate.add(Calendar.DATE, days);
	         
	         return currentDate.getTime().before(endDate) || currentDate.getTime().equals(endDate);
	    }

		public static String getStringValFromJSONString(String jsonStr, String key){
			String strVal = "";
			JSONObject obj = null;
			
			try {
				
				obj = (JSONObject)new JSONParser().parse(jsonStr);
				
			} catch (org.json.simple.parser.ParseException e) {
				
				obj = null;
			}
			
			if(obj != null) {
				
				Object objVal = obj.get(key);
				
				strVal = objVal == null?"":objVal.toString();
			}
			
			return strVal;
		}
		
		public static Long getLongValFromJSONString(String jsonStr, String key) throws org.json.simple.parser.ParseException{
			Long longVal = 0L;
			
			String strVal =  getStringValFromJSONString(jsonStr, key);
			
			if(ValidationUtil.isStringValueNotNullAndNotEmpty(strVal)){
				
				longVal = Long.parseLong(strVal);
			}
			
			return longVal;
		}
		
		public static int getIntValFromJSONString(String jsonStr, String key) throws org.json.simple.parser.ParseException{
			int longVal = 0;
			
			String strVal =  getStringValFromJSONString(jsonStr, key);
			
			if(ValidationUtil.isStringValueNotNullAndNotEmpty(strVal)){
				
				longVal = Integer.parseInt((strVal));
			}
			
			return longVal;
		}
		
	
		 
		 public static String getCurrentDate(){
				
				String s="";
				
				try{
				
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
				    s =dateFormat.format(cal.getTime());
				  
				}catch(Exception e){
					e.getStackTrace();
				}
			  return s;
			}
			
		 
		 public static String getFormattedAppDate(String date){
				
				String formattedDate="";
				
				try{
					if(date!=null && !date.isEmpty()){
						String[] splitedDate = date.split("\\s+");
						if(splitedDate!=null){
							formattedDate =  splitedDate[0];
						}
					}
				  
				}catch(Exception e){
					logger.info("error " + e.getStackTrace());
				}
			  return formattedDate;
			}
		 
		 public static String encrypt(String plainText,String keyText) throws Exception {
			 Base64 base64 = new Base64();
			 byte[] keyBytes = keyText.getBytes("UTF-8");
		     byte[] plainTextBytes = plainText.getBytes("UTF-8");
		     SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
		     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		     cipher.init(Cipher.ENCRYPT_MODE, newKey,new IvParameterSpec(new byte[16]));
		     byte[] cipherBytes = cipher.doFinal(plainTextBytes);
		     return base64.encodeAsString(cipherBytes);
		     //return new sun.misc.BASE64Encoder().encode(cipherBytes);
		 }
		 
		 public static String decrypt(String value,String keyText) throws Exception {
			 Base64 base64 = new Base64();
			 SecretKey key = new SecretKeySpec(keyText.getBytes("UTF-8"), "AES");
		     Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		     dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
		   //  byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(value);
		     byte[] dec =base64.decodeBase64(value);
		     byte[] utf8 = dcipher.doFinal(dec);
		     return new String(utf8, "UTF8");
		 }  
		 /*
		public static void main(String[] args) throws Exception {
			String enc=encrypt("ashraf","sbctech@45679@Bc");
			System.out.println("enc="+enc);
			String deenc=decrypt(enc,"sbctech@45679@Bc");
			System.out.println("dec="+deenc);
		}
		*/
		
		
		public static void validateAttachment(byte[] attachment) throws Exception
		{
				
			String contentType = new Tika().detect(attachment);
			if(!Arrays.asList(CommonConstants.ATTACHMENT_IMAGE_TYPES).contains(contentType.toLowerCase()))
				ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_ATTACHMENT_TYPE, ExceptionUtil.EXCEPTION_VALIDATION);
			
		}
		
		public static String getContentTypeOfAttachment(byte[] byteVal){
			String contType=null;
			
			contType=new Tika().detect(byteVal);
			
			return contType;
		}    

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
	
	public static String maskEmail(String email) {
		return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
	}
}

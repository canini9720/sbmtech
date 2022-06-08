package com.sbmtech.common.util;

import java.io.StringWriter;
import java.util.ResourceBundle;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbmtech.common.constant.NotificationConstants;
import com.sbmtech.dto.NotifEmailDTO;

@Component
public class NotificationUtil {

	@Autowired
	private VelocityEngine velocityEngine;

	ResourceBundle resource = null;

	private String getNotifProperty(String key) {
		String val = "";
		if (key != null && !key.isEmpty()) {
			if (resource == null) {
				resource = ResourceBundle.getBundle(NotificationConstants.NOTIF_CONSTANTS);
			}
			val = (String) resource.getString(key);
		}
		return val;
	}

	public String getServiceNotifEmailSubject() {

		return getNotifProperty(NotificationConstants.NOTIF_OTP_SUBJECT_KEY);
	}
	
	public String prepareOTPEmail(NotifEmailDTO dto){
		
		StringWriter mergedContent = new StringWriter();
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("dataHolderDTO", dto);
		
		
		velocityEngine.mergeTemplate(getNotifProperty(NotificationConstants.NOTIF_OTP_TEMPLATE_KEY), "UTF-8", velocityContext, mergedContent);
		
		return mergedContent.toString();
	}
}

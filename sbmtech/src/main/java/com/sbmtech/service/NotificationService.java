package com.sbmtech.service;

import com.sbmtech.dto.NotifEmailDTO;
import com.sbmtech.payload.response.NotificationEmailResponseDTO;

public interface NotificationService {
	public NotificationEmailResponseDTO sendOTPEmail(NotifEmailDTO dto)throws Exception;
}

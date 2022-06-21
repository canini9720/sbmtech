package com.sbmtech.service.impl;

import java.util.List;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.DocumentDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.ProfileRequest;

public class CustomeUserDetailsServiceUtil {

	public static void validatePersonalDetialRequest(ProfileRequest req,String action) throws Exception {
		if(action.equals("GET")) {
			ExceptionUtil.throwNullOrEmptyValidationException("UserId", req.getUserId(), true);
		}else if(action.equals("SAVE")){
			//TO DO
		}
		
	}

	public static DocumentRequest validateSaveDocumentDetailsRequest(DocumentRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("User Id", req.getUserId(), true);
		List<DocumentDTO> listDocumentDto=req.getDocumentDetails();
		if (listDocumentDto!=null) {
			for (int i = 0; i < listDocumentDto.size(); i++) {
				DocumentDTO documentDTO = listDocumentDto.get(i);
				if(documentDTO.getDocTypeId()==null || documentDTO.getDocTypeId()!=null && documentDTO.getDocTypeId()==0) {
					listDocumentDto.remove(i);
				}
				if(documentDTO.getDocTypeId()!=null && documentDTO.getDocTypeId()!=0) {
					ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Doc No ",documentDTO.getDocNo(), true);
					if(documentDTO.getHasAttachment()==CommonConstants.INT_ONE) {
						ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Google File Id ",documentDTO.getGoogleFileId(), true);	
					}
				}
			}
		}
		return req;
	}
}

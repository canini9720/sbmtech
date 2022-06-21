package com.sbmtech.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.common.util.ValidationUtil;
import com.sbmtech.dto.DocumentDTO;
import com.sbmtech.dto.EduDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.EduRequest;
import com.sbmtech.payload.request.ProfileRequest;

public class CustomeUserDetailsServiceUtil {

	public static void validatePersonalDetialRequest(ProfileRequest req,String action) throws Exception {
		if(action.equals("GET")) {
			ExceptionUtil.throwNullOrEmptyValidationException("UserId", req.getUserId(), true);
		}else if(action.equals("SAVE")){
			if(req.getPersonDetails()!=null) {
				if(StringUtils.isNotBlank(req.getPersonDetails().getDob())) {
					if(!ValidationUtil.validateDateFormat(req.getPersonDetails().getDob(), CommonConstants.DATE_ddMMyyyy)){
						ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_DATE_FORMAT, ExceptionUtil.EXCEPTION_VALIDATION);
					}	
				}
					
			}
			
		}
		
	}

	public static DocumentRequest validateSaveDocumentDetailsRequest(DocumentRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("User Id", req.getUserId(), true);
		List<DocumentDTO> listDocumentDto=req.getDocumentDetails();
		if (listDocumentDto!=null) {
			for (int i = 0; i < listDocumentDto.size(); i++) {
				DocumentDTO documentDTO = listDocumentDto.get(i);
				ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Doc Type Id",documentDTO.getDocTypeId(), true);
				/*if(documentDTO.getDocTypeId()==null || documentDTO.getDocTypeId()!=null && documentDTO.getDocTypeId()==0) {
					listDocumentDto.remove(i);
					break;
				}*/
				if(documentDTO.getDocTypeId()!=null && documentDTO.getDocTypeId()!=0) {
					ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Doc No ",documentDTO.getDocNo(), true);
					if(documentDTO.getHasAttachment()==CommonConstants.INT_ONE) {
						ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Google File Id ",documentDTO.getGoogleFileId(), true);	
						ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Content Type ",documentDTO.getContentType(), true);
					}
				}
			}
		}
		return req;
	}
	
	public static EduRequest validateSaveEduRequest(EduRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("User Id", req.getUserId(), true);
		List<EduDTO> listEduDto=req.getEduDetails();
		if (listEduDto!=null) {
			for (int i = 0; i < listEduDto.size(); i++) {
				EduDTO eduDTO = listEduDto.get(i);
				ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Doc Type Id",eduDTO.getDocTypeId(), true);
				/*if(eduDTO.getDocTypeId()==null || eduDTO.getDocTypeId()!=null && eduDTO.getDocTypeId()==0) {
					listEduDto.remove(i);
					break;
				}*/
				if(eduDTO.getDocTypeId()!=null && eduDTO.getDocTypeId()!=0) {
					//ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Qualification",eduDTO.getQualification(), true);
					//ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Institute",eduDTO.getInstitute(), true);
					//ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Passed Year",eduDTO.getPassedYear(), true);
					//ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Grade",eduDTO.getGrade(), true);
					if(eduDTO.getHasAttachment()==CommonConstants.INT_ONE) {
						ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Google File Id ",eduDTO.getGoogleFileId(), true);	
						ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" Content Type ",eduDTO.getContentType(), true);
					}
				}
			}
		}
		return req;
	}
}

package com.sbmtech.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.RoleDTO;
import com.sbmtech.model.BankMaster;
import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.PaidBasisMaster;
import com.sbmtech.model.Role;
import com.sbmtech.model.WorkTimeMaster;
import com.sbmtech.payload.response.GroupActivityResponse;
import com.sbmtech.service.DSSService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dss")
public class DSSController {
	
	@Autowired
	DSSService dssService;
	
	@GetMapping("/getBloodGroup")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<BloodGroup> getBloodGroup() throws Exception {
		return dssService.getAllBloodGroup();
	}
	
	@GetMapping("/getAllContactType")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<ContactTypeMaster> getAllContactType() throws Exception {
		return dssService.getAllContactType();
	}
	
	@GetMapping("/getAllDocType")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.group)  or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<DocTypeMaster> getAllDocType() throws Exception {
		return dssService.getAllDocType();
	}
	/*
	@GetMapping("/getMemDoc")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<DocTypeMaster> getMemDoc() throws Exception {
		return dssService.getMemDoc();
	}
	
	@GetMapping("/getMemEduDoc")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<DocTypeMaster> getMemEduDoc() throws Exception {
		return dssService.getMemEduDoc();
	}
	
	@GetMapping("/getDocTypeForCompany")
	@PreAuthorize("hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<DocTypeMaster> getDocTypeForCompany() throws Exception {
		return dssService.getDocTypeForCompany();
	}
	*/
	@GetMapping("/getAllRole")
	@PreAuthorize("hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<Role> getAllRole() throws Exception {
		return dssService.getAllRole();
	}
	
	@GetMapping("/getAllRoleGroupAdmin")
	@PreAuthorize("hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<RoleDTO> getAllRoleGroupAdmin() throws Exception {
		return dssService.getAllRoleGroupAdmin();
	}
	
	@GetMapping("/getWorkTimeMaster")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<WorkTimeMaster> getWorkTimeMaster() throws Exception {
		return dssService.getWorkTimeMaster();
	}

	
	@GetMapping("/getPaidBasisMaster")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<PaidBasisMaster> getPaidBasisMaster() throws Exception {
		return dssService.getPaidBasisMaster();
	}
	
	@GetMapping("/getBankMaster")
	@PreAuthorize("hasRole(@securityService.member) or hasRole(@securityService.group) or hasRole('COMPANY') or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<BankMaster> getBankMaster() throws Exception {
		return dssService.getBankMaster();
	}

	@GetMapping(value="getAllGroupActivitesActive", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.admin) ")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String  getAllGroupActivitesActive() throws Exception{
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		GroupActivityResponse resp= dssService.getAllGroupActivitesActive();
		if (resp != null) {
			respObj.put("getAllGroupActivitesActive", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put("getAllGroupActivitesActive", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	 }

}

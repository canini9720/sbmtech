package com.sbmtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbmtech.model.BankMaster;
import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.PaidBasisMaster;
import com.sbmtech.model.Role;
import com.sbmtech.model.WorkTimeMaster;
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
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<BloodGroup> getBloodGroup() throws Exception {
		return dssService.getAllBloodGroup();
	}
	
	@GetMapping("/getAllContactType")
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<ContactTypeMaster> getAllContactType() throws Exception {
		return dssService.getAllContactType();
	}
	
	@GetMapping("/getAllDocType")
	@PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<Role> getAllRole() throws Exception {
		return dssService.getAllRole();
	}
	
	@GetMapping("/getWorkTimeMaster")
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<WorkTimeMaster> getWorkTimeMaster() throws Exception {
		return dssService.getWorkTimeMaster();
	}

	
	@GetMapping("/getPaidBasisMaster")
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<PaidBasisMaster> getPaidBasisMaster() throws Exception {
		return dssService.getPaidBasisMaster();
	}
	
	@GetMapping("/getBankMaster")
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public List<BankMaster> getBankMaster() throws Exception {
		return dssService.getBankMaster();
	}


}

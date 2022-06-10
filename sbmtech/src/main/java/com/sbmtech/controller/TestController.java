package com.sbmtech.controller;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbmtech.common.constant.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/member")
	@PreAuthorize("hasRole('MEMBER')  or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}
	
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/group")
	@PreAuthorize("hasRole('GROUP')  or hasRole('ADMIN')")
	public String groupAccess() {
		loggerInfo.info("group logged");
		return "Group Board.";
	}
	
	@GetMapping("/company")
	@PreAuthorize("hasRole('COMPANY')  or hasRole('ADMIN')")
	public String companyAccess() {
		return "Company Board.";
	}
	
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
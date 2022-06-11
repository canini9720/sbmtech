package com.sbmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbmtech.service.DSSService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/common")
public class CommonController {
	
	@Autowired
	DSSService dssService;
	
	@GetMapping("/saveAttachmentCloud")
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	public String saveAttachmentCloud() throws Exception {
		return null;
	}

}


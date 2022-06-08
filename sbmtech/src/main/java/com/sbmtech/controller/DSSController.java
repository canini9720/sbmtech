package com.sbmtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbmtech.model.BloodGroup;
import com.sbmtech.service.DSSService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dss")
public class DSSController {
	
	@Autowired
	DSSService dssService;
	
	@GetMapping("/getBloodGroup")
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	public List<BloodGroup> getBloodGroup() throws Exception {
		return dssService.getAll();
	}

}

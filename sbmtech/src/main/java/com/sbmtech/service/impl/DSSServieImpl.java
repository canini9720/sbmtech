package com.sbmtech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.Role;
import com.sbmtech.repository.BloodRepository;
import com.sbmtech.repository.ContactTypeRepository;
import com.sbmtech.repository.DocTypeRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.service.DSSService;

@Service
public class DSSServieImpl implements DSSService {
	
	@Autowired
	BloodRepository bloodRepo;
	
	@Autowired
	DocTypeRepository docTypeRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	ContactTypeRepository contactTypeRepo;

	@Override
	public List<BloodGroup> getAllBloodGroup() throws Exception {
		return bloodRepo.findAll();
	}

	@Override
	public List<DocTypeMaster> getAllDocType() throws Exception {
		return docTypeRepo.findAll();
	}

	@Override
	public List<Role> getAllRole() throws Exception {
		return roleRepo.findAll();
	}
	
	@Override
	public List<ContactTypeMaster> getAllContactType() throws Exception {
		return contactTypeRepo.findAll();
	}

}

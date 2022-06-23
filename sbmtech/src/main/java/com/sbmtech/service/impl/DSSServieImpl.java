package com.sbmtech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.PaidBasisMaster;
import com.sbmtech.model.Role;
import com.sbmtech.model.WorkTimeMaster;
import com.sbmtech.repository.BloodRepository;
import com.sbmtech.repository.ContactTypeRepository;
import com.sbmtech.repository.DocTypeRepository;
import com.sbmtech.repository.PaidBasisMasterRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.WorkTimeRepository;
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
	
	@Autowired
	WorkTimeRepository workTimeRepo;
	
	@Autowired
	PaidBasisMasterRepository paidBasisMasRepo;

	@Override
	public List<BloodGroup> getAllBloodGroup() throws Exception {
		return bloodRepo.findAll();
	}

	@Override
	public List<DocTypeMaster> getAllDocType() throws Exception {
		return  docTypeRepo.findByActive(1);

	}

	@Override
	public List<Role> getAllRole() throws Exception {
		return roleRepo.findAll();
	}
	
	@Override
	public List<ContactTypeMaster> getAllContactType() throws Exception {
		return contactTypeRepo.findAll();
	}

	@Override
	public List<DocTypeMaster> getDocTypeForCompany() throws Exception {

		List<DocTypeMaster> list= docTypeRepo.findByForGroupCompanyAndActive(1,1);
		return list;
	}

	@Override
	public List<WorkTimeMaster> getWorkTimeMaster() throws Exception {
		List<WorkTimeMaster> list= workTimeRepo.findByActive(1);
		return list;
	}
	@Override
	public List<PaidBasisMaster> getPaidBasisMaster()throws Exception{
		List<PaidBasisMaster> list= paidBasisMasRepo.findByActive(1);
		return list;
	}

}

package com.sbmtech.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.GroupActivityDTO;
import com.sbmtech.dto.GroupSubActivityDTO;
import com.sbmtech.model.BankMaster;
import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.GroupActivityEntity;
import com.sbmtech.model.PaidBasisMaster;
import com.sbmtech.model.Role;
import com.sbmtech.model.WorkTimeMaster;
import com.sbmtech.payload.response.GroupActivityResponse;
import com.sbmtech.repository.BankMasterRepository;
import com.sbmtech.repository.BloodRepository;
import com.sbmtech.repository.ContactTypeRepository;
import com.sbmtech.repository.DocTypeRepository;
import com.sbmtech.repository.GroupActivityMasterRepository;
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
	
	@Autowired
	BankMasterRepository bankMasRepo;
	
	@Autowired
	GroupActivityMasterRepository groupActivityMasterRepo;
	
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

	@Override
	public List<BankMaster> getBankMaster() throws Exception {
		List<BankMaster> list= bankMasRepo.findByActive(1);
		return list;
	}

	@Override
	public List<DocTypeMaster> getMemDoc() throws Exception {
		return  docTypeRepo.findByForMemDocAndActive(1,1);
		
	}

	@Override
	public List<DocTypeMaster> getMemEduDoc() throws Exception {
		return docTypeRepo.findByForMemDocEduAndActive(1,1);
	}
	
	@Override
	public GroupActivityResponse getAllGroupActivitesActive() {
		GroupActivityResponse resp=null;
		List<GroupActivityEntity> list=groupActivityMasterRepo.findByActive(CommonConstants.INT_ONE);
		List<GroupActivityDTO> grpActDTO = list.stream().
			     map(s -> {
			    	 GroupActivityDTO u = new GroupActivityDTO();
			    u.setGroupActivityId(s.getGrpActivityId());
			    u.setGroupActivity(s.getGrpActivity());
			    
			    List<GroupSubActivityDTO> grpSubActDTO = s.getGroupSubActivityList().stream()
			    		.filter(sa->sa.getActive()==CommonConstants.INT_ONE)
			    		.map(sa->{
				    		GroupSubActivityDTO subAct=new GroupSubActivityDTO();
				    		subAct.setGroupSubActivityId(sa.getGrpSubActivityId());
				    		subAct.setGroupSubActivity(sa.getGrpSubActivity());
				    		return subAct;
			    	}).collect(Collectors.toList());
			    u.setListGroupSubActivity(grpSubActDTO);
			    return u;
			    }).collect(Collectors.toList());
		if(list!=null && !list.isEmpty()) {
			resp=new GroupActivityResponse();
			resp.setGroupActivityList(grpActDTO);
		}
		
		return resp;
	}
	

}

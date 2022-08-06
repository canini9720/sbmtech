package com.sbmtech.service;

import java.util.List;

import com.sbmtech.dto.RoleDTO;
import com.sbmtech.model.BankMaster;
import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.PaidBasisMaster;
import com.sbmtech.model.Role;
import com.sbmtech.model.WorkTimeMaster;
import com.sbmtech.payload.response.GroupActivityResponse;

public interface DSSService {
	
	public List<BloodGroup> getAllBloodGroup()throws Exception;
	public List<DocTypeMaster> getAllDocType()throws Exception;
	public List<Role> getAllRole()throws Exception;
	public List<ContactTypeMaster> getAllContactType() throws Exception;
	
	public List<DocTypeMaster> getDocTypeForCompany()throws Exception;
	public List<WorkTimeMaster> getWorkTimeMaster()throws Exception;
	public List<PaidBasisMaster> getPaidBasisMaster()throws Exception;
	public List<BankMaster> getBankMaster()throws Exception;
	public List<DocTypeMaster> getMemDoc()throws Exception;
	public List<DocTypeMaster> getMemEduDoc()throws Exception;
	public GroupActivityResponse getAllGroupActivitesActive();
	public List<RoleDTO> getAllRoleGroupAdmin()throws Exception;
}

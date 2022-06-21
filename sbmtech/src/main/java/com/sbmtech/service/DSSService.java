package com.sbmtech.service;

import java.util.List;

import com.sbmtech.model.BloodGroup;
import com.sbmtech.model.ContactTypeMaster;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.Role;

public interface DSSService {
	
	public List<BloodGroup> getAllBloodGroup()throws Exception;
	public List<DocTypeMaster> getAllDocType()throws Exception;
	public List<Role> getAllRole()throws Exception;
	public List<ContactTypeMaster> getAllContactType() throws Exception;
	
	public List<DocTypeMaster> getDocTypeForCompany()throws Exception;

}

package com.sbmtech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbmtech.model.BloodGroup;
import com.sbmtech.repository.BloodRepository;
import com.sbmtech.service.DSSService;

@Service
public class DSSServieImpl implements DSSService {
	
	@Autowired
	BloodRepository bloodRepo;

	@Override
	public List<BloodGroup> getAll() throws Exception {
		// TODO Auto-generated method stub
		return bloodRepo.findAll();
	}

}

package com.sbmtech.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sbmtech.dto.FileItemDTO;



public interface CommonService {
	public String createUserFolder(String userId) throws Exception ;
	public String saveFile(MultipartFile file,Long userId,Integer docTypeId)throws Exception;
	public List<FileItemDTO> getAllFileByUserId(Long userId)throws Exception;
	

}

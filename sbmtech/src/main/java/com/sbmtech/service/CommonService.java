package com.sbmtech.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.exception.IntrusionException;
import com.sbmtech.payload.response.GDriveResponse;



public interface CommonService {
	public void checkIntrusion(HttpServletRequest request)throws IntrusionException ;
	public String createUserFolder(String userId) throws Exception ;
	public GDriveResponse saveFile(MultipartFile file,Long userId,Integer docTypeId)throws Exception;
	public List<FileItemDTO> getAllFileByUserId(Long userId)throws Exception;
	public FileItemDTO getFileByUserIdAndDocTypeId(Long userId,Integer docTypeId)throws Exception;
	public void deleteFile(Long userId, Integer docTypeId) throws Exception ;
	public List<FileItemDTO> getAllFileByUser(Long userId,Integer docTypeId)throws Exception;

}

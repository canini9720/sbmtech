package com.sbmtech.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.sbmtech.dto.ActiveMemberDTO;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.exception.IntrusionException;
import com.sbmtech.payload.response.GDriveResponse;



public interface CommonService {
	public void checkIntrusion(HttpServletRequest request)throws IntrusionException ;
	public String createSession(Long userId) throws Exception ;
	public void signout(String sessionId) throws Exception ;
	public String createUserFolder(String userId) throws Exception ;
	public GDriveResponse saveFile(MultipartFile file,Long userId,Integer docTypeId)throws Exception;
	public List<FileItemDTO> getAllFileByUserId(Long userId)throws Exception;
	public FileItemDTO getFileByUserIdAndDocTypeId(Long userId,Integer docTypeId);
	public void deleteFile(Long userId, Integer docTypeId) throws Exception ;
	public void deleteFileByGFileId(Long userId, Integer docTypeId,String gFileId) throws Exception ;
	public List<FileItemDTO> getAllFileByUser(Long userId,Integer docTypeId)throws Exception;
	public boolean checkTokenExist(HttpServletRequest request)throws Exception;
	public List<ActiveMemberDTO> getAllActiveMembers()throws Exception;
	public List<ActiveMemberDTO> getAllActiveMembers(String username);

}

package com.sbmtech.controller;

import java.util.List;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.payload.response.GDriveResponse;
import com.sbmtech.service.CommonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	
	@PostMapping(value="/saveAttachmentCloud", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)  or hasRole(@securityService.group) or hasRole(@securityService.company) or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String saveAttachmentCloud(@RequestParam("attachment") MultipartFile file, 
			@RequestParam(name = "userId") Long userId,
			@RequestParam(name = "docTypeId") Integer docTypeId) throws Exception{
		
		GDriveResponse resp = commonService.saveFile(file, userId, docTypeId);
		JSONObject respObj = new JSONObject();
		respObj.put("response", resp);
		
		Gson gson = new Gson();
        return gson.toJson(respObj);
	}
	
	
	@GetMapping(value="/getUserAttachmentCloudByDocId/{docTypeId}",  produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)  or hasRole(@securityService.group) or hasRole(@securityService.company) or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String getUserAttachmentCloud(@RequestParam(name = "userId") Long userId,@PathVariable Integer docTypeId ) throws Exception{
		
		List<FileItemDTO> listAllFiles = null;
		listAllFiles=commonService.getAllFileByUser(userId,docTypeId);
		JSONObject respObj = new JSONObject();
		if(listAllFiles!=null) {
			respObj.put("userFiles", listAllFiles);	
		}else {
			respObj.put("userFiles", "No file Found");
		}
		Gson gson = new Gson();
        return gson.toJson(respObj);
	}
	/*
	@GetMapping(value="/getUserAttachmentCloudTest",  produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)  or hasRole(@securityService.group) or hasRole(@securityService.company) or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String getUserAttachmentCloudTest(@RequestParam(name = "userId") Long userId,@RequestParam(name = "docTypeId") Integer docTypeId,HttpServletRequest request ) throws Exception{
		//commonService.checkIntrusion(request);
		List<FileItemDTO> listAllFiles = null;
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl customUser = (UserDetailsImpl)auth.getPrincipal();
		Long userId2 = customUser.getUserId();
		listAllFiles=commonService.getAllFileByUser(userId,docTypeId);
		JSONObject respObj = new JSONObject();
		if(listAllFiles!=null) {
			respObj.put("userFiles", listAllFiles);	
		}else {
			respObj.put("userFiles", "No file Found");
		}
		Gson gson = new Gson();
        return gson.toJson(respObj);
	}
*/
}


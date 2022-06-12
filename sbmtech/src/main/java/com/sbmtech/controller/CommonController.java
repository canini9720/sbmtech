package com.sbmtech.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.payload.request.FileRequest;
import com.sbmtech.service.CommonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	
	@RequestMapping(value="/saveAttachmentCloud", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	public String saveAttachmentCloud(@RequestParam("attachment") MultipartFile file, 
			@RequestParam(name = "userId") Long userId,
			@RequestParam(name = "docTypeId") Integer docTypeId) throws Exception{
		
		String attachId = commonService.saveFile(file, userId, docTypeId);
		JSONObject respObj = new JSONObject();
		respObj.put("attachmentId", attachId);
		
		Gson gson = new Gson();
        return gson.toJson(respObj);
	}
	
	
	@RequestMapping(value="/getUserAttachmentCloud",  produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	public String saveAttachmentCloud(@RequestBody FileRequest fileRequest) throws Exception{
		
		List<FileItemDTO> listAllFiles = commonService.getAllFileByUserId( fileRequest.getUserId());
		
		JSONObject respObj = new JSONObject();
		respObj.put("userFiles", listAllFiles);
		
		Gson gson = new Gson();
        return gson.toJson(respObj);
	}

}


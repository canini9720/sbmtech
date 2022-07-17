package com.sbmtech.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionBusinessConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.common.util.SessionTokenGenerator;
import com.sbmtech.dto.ActiveMemberDTO;
import com.sbmtech.dto.EduDTO;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.exception.IntrusionException;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.ERole;
import com.sbmtech.model.EducationEntity;
import com.sbmtech.model.GDriveUser;
import com.sbmtech.model.User;
import com.sbmtech.model.UserSessionEntity;
import com.sbmtech.payload.response.GDriveResponse;
import com.sbmtech.repository.DocTypeRepository;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.repository.UserSessionRepository;
import com.sbmtech.security.services.UserDetailsImpl;
import com.sbmtech.service.CommonService;


@Service
@DependsOn("AppSystemProp")
@Transactional
public class CommonServiceImpl implements CommonService {
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);

	
	private String gdriveFilePath="";
	private String gdriveFileName="";
	private String gdriveAcct="";
	private String gdriveAppName="";
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
	private String gdriveBaseFolder="";
	
	
	static Drive driveService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GDriveUserRepository gDriveRepo;
	
	@Autowired
	DocTypeRepository docTypeRepo;

	
	@Autowired
	UserSessionRepository userSessionRepo;
	
		
	@PostConstruct
	public void initialize() throws GeneralSecurityException, IOException {
		
		gdriveFilePath = AppSystemPropImpl.props.get("gdrive.file.path");
		gdriveFileName = AppSystemPropImpl.props.get("gdrive.file.name");
		gdriveAcct = AppSystemPropImpl.props.get("gdrive.account");
		gdriveAppName = AppSystemPropImpl.props.get("gdrive.app.name");
		gdriveBaseFolder = AppSystemPropImpl.props.get("gdrive.base.folder");
		
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		java.io.File gdrivefile= new java.io.File(gdriveFilePath+gdriveFileName);
		InputStream resourceAsStream = new FileInputStream(gdrivefile);
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(resourceAsStream).createScoped(SCOPES).createDelegated(gdriveAcct));
		driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
				.setApplicationName(gdriveAppName).build();
		
		
		loggerInfo.info("credential success Post construct");
	}
	
	@Override
	public void deleteFile(Long userId, Integer docTypeId) throws Exception {
		try {
			GDriveUser gDriveUser = gDriveRepo.findById(userId).get();
			String pageToken = null;
			 FileList result = driveService.files().list()
				      .setQ("parents in '"+gDriveUser.getParentId()+"' and name = '"+docTypeId+"'")
				      .setSpaces("drive")
				      .setFields("nextPageToken, files(id, name)")
				      .setPageToken(pageToken)
				      .execute();
	        for (File gFl : result.getFiles()){ 
	        	String id = gFl.getId();
	            driveService.files().delete(gFl.getId()).execute();
	        } 
		}catch (Exception ex) {
		    throw ex;
		}
	}
	public void deleteFileByGFileId(Long userId, Integer docTypeId,String gFileId) throws Exception {
		try {
			GDriveUser gDriveUser = gDriveRepo.findById(userId).get();
			String pageToken = null;
			 FileList result = driveService.files().list()
				      .setQ("parents in '"+gDriveUser.getParentId()+"' and id = '"+gFileId+"'")
				      .setSpaces("drive")
				      .setFields("nextPageToken, files(id, name)")
				      .setPageToken(pageToken)
				      .execute();
	        for (File gFl : result.getFiles()){ 
	        	String id = gFl.getId();
	            driveService.files().delete(gFl.getId()).execute();
	        } 
		}catch (Exception ex) {
		    throw ex;
		}
	}

	@Override
	public GDriveResponse saveFile(MultipartFile file,Long userId, Integer docTypeId) throws Exception {
		GDriveResponse gDriveResp=null;
		try {
			validateRequest(file,userId,docTypeId);
			
			Optional<DocTypeMaster> docTypeMasterOp =docTypeRepo.findById(docTypeId);
			if(!docTypeMasterOp.isPresent()) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_DOC_TYPE_ID, ExceptionUtil.EXCEPTION_VALIDATION);
			}
			Optional<GDriveUser> gDriveUserOp = gDriveRepo.findById(userId);
			if(gDriveUserOp.isEmpty()) {
				Optional<User> userOp=userRepository.findById(userId);
				if(userOp.isPresent()) {
					String parentId=this.createUserFolder(String.valueOf(userId));
					GDriveUser gdriveUser=new GDriveUser();
					gdriveUser.setUserId(userId);
					gdriveUser.setParentId(parentId);
					gDriveRepo.saveAndFlush(gdriveUser);
				}else {
					ExceptionUtil.throwException(ExceptionBusinessConstants.USER_NOT_FOUND, ExceptionUtil.EXCEPTION_BUSINESS);
				}
				gDriveUserOp = gDriveRepo.findById(userId);
			}
			
			if(gDriveUserOp.isPresent()) {
				String contentType = new Tika().detect(file.getBytes());
				GDriveUser gDriveUser=gDriveUserOp.get();
				String pageToken = null;
				FileList result = driveService.files().list()
					      .setQ("parents in '"+gDriveUser.getParentId()+"' and name = '"+docTypeId+"'")
					      .setSpaces("drive")
					      .setFields("nextPageToken, files(id, name)")
					      .setPageToken(pageToken)
					      .execute();
		        for (File gFl : result.getFiles()){ 
		        	String id = gFl.getId();
		            driveService.files().delete(gFl.getId()).execute();
		        } 
				String folderId = gDriveUser.getParentId();
			    String fileName=String.valueOf(docTypeId);
			    if (null != file) {
			    	File fileMetadata = new File();
			        fileMetadata.setParents(Collections.singletonList(folderId));
			        fileMetadata.setName(fileName);
			        File uploadFile = driveService
			              .files()
			              .create(fileMetadata, new InputStreamContent(
			                    file.getContentType(),
			                    new ByteArrayInputStream(file.getBytes()))
			              )
			              .setFields("id").execute();
			      
			        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
					String base64 =  new String(encoder.encode(file.getBytes()));
			        gDriveResp=new GDriveResponse(CommonConstants.INT_ONE);
			        gDriveResp.setGFileId(uploadFile.getId());
			        gDriveResp.setDocTypeId(docTypeId);
			        gDriveResp.setUserId(userId);
			        gDriveResp.setContentType(contentType);
			        gDriveResp.setBase64(base64);
			     }
			  } 
			}catch (Exception ex) {
			    throw ex;
			}
		  return gDriveResp;
	}
	



	private void validateRequest(MultipartFile file, Long userId, Integer docTypeId)throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("User Id", userId, true);
		ExceptionUtil.throwNullOrEmptyValidationException("Doc Type Id", docTypeId, true);
		
		if(file!=null && file.getBytes()!=null && file.getBytes().length>0) {
			String contentType = new Tika().detect(file.getBytes());
			if(!Arrays.asList(CommonConstants.ATTACHMENT_TYPES).contains(contentType.toLowerCase())) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_ATTACHMENT_TYPE, ExceptionUtil.EXCEPTION_VALIDATION);
			}
			
			long size = file.getBytes() .length;
			size=(size/1024)/1024;//MB
			if(size>CommonConstants.FILE_SIZE) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_ATTACHMENT_SIZE, ExceptionUtil.EXCEPTION_VALIDATION);
			}
		}else {
			ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_ATTACHMENT, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		
		
	}

	@Override
	public List<FileItemDTO> getAllFileByUserId(Long userId) throws Exception {
		List<DocTypeMaster> allDocType=docTypeRepo.findAll();	
		GDriveUser gDriveUser =gDriveRepo.findById(userId).get();
		String userFolderId = gDriveUser.getParentId();
		List<File> listGfiles=listFolderContent(userFolderId);
		List<FileItemDTO> listFileItem=new ArrayList<FileItemDTO>();
		for (File file : listGfiles) {
			FileItemDTO item = new FileItemDTO();
			item.setId(file.getId());
			item.setName(file.getName());
			item.setContentType(file.getMimeType());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			driveService.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);

			java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
			String base64 =  new String(encoder.encode(outputStream.toByteArray()));
			item.setBase64String(base64);
			
			for(DocTypeMaster docType:allDocType) {
				if(file.getName().equals(String.valueOf(docType.getDocTypeId()))) {
					item.setDocTypeId(docType.getDocTypeId());
					item.setDocTypeDesc(docType.getFileDesc());
					break;
				}
			}
			listFileItem.add(item);
		}
		return listFileItem;
	}
	
	public List<File> listFolderContent(String parentId) throws IOException, GeneralSecurityException {
		  if(parentId == null){
		     parentId = "root";
		  }
		  String query = "'" + parentId + "' in parents";
		  FileList result = driveService.files().list()
		        .setQ(query)
		        .setPageSize(10)
		        .setFields("nextPageToken, files(id, name,mimeType)")
		        .execute();
		  
		  return result.getFiles();
	}
	
	public File getFileByName(String parentId,String fileName) throws IOException, GeneralSecurityException {
		String docTypeId="";
		  if(parentId == null){
		     parentId = "root";
		  }
		  if(StringUtils.isNotBlank(fileName)) {
			  docTypeId=fileName;
		  }
		  String query = "'" + parentId + "' in parents"+" and name = '"+docTypeId+"'";
		  FileList result = driveService.files().list()
		        .setQ(query)
		        .setPageSize(10)
		        .setFields("nextPageToken, files(id, name)")
		        .execute();

		  if(result!=null && !result.isEmpty()) {
			 List<File> listFiles= result.getFiles();
			 if(listFiles!=null && !listFiles.isEmpty()) {
				 return listFiles.get(0);
			 }
		  }
		  return null;
	}
	
	
/*	
	public String getFolderId(String path) throws Exception {
		  String parentId = null;
		  String[] folderNames = path.split("/");
		 
		  for (String name : folderNames) {
		     parentId = findOrCreateFolder(parentId, name, driveService);
		  }
		  return parentId;
		}
	
	private String findOrCreateFolder(String parentId, String folderName, Drive driveInstance) throws Exception {
		  String folderId = searchFolderId(parentId, folderName, driveInstance);
		  // Folder already exists, so return id
		  if (folderId != null) {
		     return folderId;
		  }
		  //Folder dont exists, create it and return folderId
		  File fileMetadata = new File();
		  fileMetadata.setMimeType("application/vnd.google-apps.folder");
		  fileMetadata.setName(folderName);
		 
		  if (parentId != null) {
			  fileMetadata.setParents(Arrays.asList(gdriveBaseFolder)); 
		  }
		  return driveInstance.files().create(fileMetadata)
		        .setFields("id")
		        .execute()
		        .getId();
		}
	
	private String searchFolderId(String folderName, Drive service) throws Exception {
		  return searchFolderId(null, folderName, service);
	}
	
	private String searchFolderId(String parentId, String folderName, Drive service) throws Exception {
		  String folderId = null;
		  String pageToken = null;
		  FileList result = null;
		  File fileMetadata = new File();
		  fileMetadata.setMimeType("application/vnd.google-apps.folder");
		  fileMetadata.setName(folderName);
		  do {
		     String query = " mimeType = 'application/vnd.google-apps.folder' ";
		     if (parentId == null) {
		        query = query + " and 'root' in parents";
		     } else {
		        query = query + " and '" + parentId + "' in parents";
		     }
		     result = service.files().list().setQ(query)
		           .setSpaces("drive")
		           .setFields("nextPageToken, files(id, name)")
		           .setPageToken(pageToken)
		           .execute();
		     for (File file : result.getFiles()) {
		        if (file.getName().equalsIgnoreCase(folderName)) {
		           folderId = file.getId();
		        }
		     }
		     pageToken = result.getNextPageToken();
		  } while (pageToken != null && folderId == null);
		  return folderId;
	}
*/

	public String createUserFolder(String userId) throws Exception {
		  
		  //Folder dont exists, create it and return folderId
		  File fileMetadata = new File();
		  fileMetadata.setMimeType("application/vnd.google-apps.folder");
		  fileMetadata.setName(userId); //folderName
		  fileMetadata.setParents(Arrays.asList(gdriveBaseFolder)); 
		  return driveService.files().create(fileMetadata)
		        .setFields("id")
		        .execute()
		        .getId();
	}



	@Override
	public FileItemDTO getFileByUserIdAndDocTypeId(Long userId, Integer docTypeId) throws Exception {
		FileItemDTO item=null;
		List<DocTypeMaster> allDocType=docTypeRepo.findAll();	
		GDriveUser gDriveUser =gDriveRepo.findById(userId).get();
		String userFolderId = gDriveUser.getParentId();
		File gFile=getFileByName(userFolderId,String.valueOf(docTypeId));
		if(gFile!=null) {	
			item = new FileItemDTO();
			item.setId(gFile.getId());
			item.setName(gFile.getName());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			driveService.files().get(gFile.getId()).executeMediaAndDownloadTo(outputStream);
			
	
			java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
			String base64 =  new String(encoder.encode(outputStream.toByteArray()));
			item.setBase64String(base64);
			
			for(DocTypeMaster docType:allDocType) {
				if(gFile.getName().equals(String.valueOf(docType.getDocTypeId()))) {
					item.setDocTypeId(docType.getDocTypeId());
					item.setDocTypeDesc(docType.getFileDesc());
					break;
				}
			}
		}
		return item;	
	}

	@Override
	public List<FileItemDTO> getAllFileByUser(Long userId, Integer docTypeId) throws Exception {
		List<FileItemDTO> listAllFiles = null;
		ExceptionUtil.throwNullOrEmptyValidationException("UserId", userId, true);
		if(userId!=null && userId!=0 &&  docTypeId!=null && docTypeId!=0) {
			listAllFiles=new ArrayList<FileItemDTO>();
			FileItemDTO fileItem=this.getFileByUserIdAndDocTypeId(userId,docTypeId);
			listAllFiles.add(fileItem);
		}else if(docTypeId==null ||( docTypeId!=null && docTypeId==0)) {
			listAllFiles=this.getAllFileByUserId(userId);	
		}
		return listAllFiles;
	}

	@Override
	public void checkIntrusion(HttpServletRequest request) throws IntrusionException {
		if(request.getParameter("userId")!=null) {
			Long userId=Long.valueOf(request.getParameter("userId"));
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl customUser = (UserDetailsImpl)auth.getPrincipal();
			Long loggedUser = customUser.getUserId();
			loggerInfo.info("Param userid="+userId+" , Logged User="+loggedUser);
			System.out.println("Param userid="+userId+" , Logged User="+loggedUser);
			if(auth != null && auth.getAuthorities().stream().anyMatch(a -> !a.getAuthority().equals(ERole.ROLE_ADMIN.toString())) && userId!=loggedUser) {
				throw new IntrusionException("Invalid access");
			}
		}
		
	}

	@Override
	public String createSession(Long userId) throws Exception {
		String sessToken= new SessionTokenGenerator().generateId(30);
		UserSessionEntity userSessionEnt=new UserSessionEntity();
		userSessionEnt.setUserId(userId);
		userSessionEnt.setSessionToken(sessToken);
		userSessionEnt.setTokenCreatedDate(new Date());
		userSessionRepo.saveAndFlush(userSessionEnt);
		return sessToken;
	}

	@Override
	public boolean checkTokenExist(HttpServletRequest request) throws Exception {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl customUser = (UserDetailsImpl)auth.getPrincipal();
		if(customUser!=null && customUser.getUserId()!=null) {
			Optional<UserSessionEntity> userSessionOpEnt=userSessionRepo.findById(customUser.getUserId());
			if(userSessionOpEnt.isPresent()) {
				UserSessionEntity userSessionEnt=userSessionOpEnt.get();
				System.out.println(userSessionEnt);
				System.out.println("session exists for user="+customUser.getUserId());
				return true;
			}
		}
		return false;
		
		
	}

	@Override
	public void signout(String sessionId) throws Exception {
		userSessionRepo.deleteUserSessionEntityBySessionToken(sessionId);
		
	}

	@Override
	public List<ActiveMemberDTO> getAllActiveMembers() throws Exception {
		List<User> activeMembers=userRepository.getAllActiveMembers();
		List<ActiveMemberDTO> asDto = activeMembers.stream().filter(Objects::nonNull).map(new Function<User, ActiveMemberDTO>() {
		    @SuppressWarnings("deprecation")
			@Override
		    public ActiveMemberDTO apply(User userEnt) {
		    	ActiveMemberDTO activeDto=null;
	    		activeDto=new ActiveMemberDTO();
	    		activeDto.setUserId(userEnt.getUserId());
	    		activeDto.setMemberName(StringUtils.capitalise((userEnt.getFirstname()+" "+CommonUtil.getStringValofObject(userEnt.getLastname())).trim()));
	    		
	    		
		    	return activeDto;
		    }
		}).collect(Collectors.toList());
		return asDto;
	}



	
	

}

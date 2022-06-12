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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
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
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.GDriveUser;
import com.sbmtech.repository.DocTypeRepository;
import com.sbmtech.repository.GDriveUserRepository;
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
	GDriveUserRepository gDriveRepo;
	
	@Autowired
	DocTypeRepository docTypeRepo;
	
	
	
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
	public String saveFile(MultipartFile file,Long userId, Integer docTypeId) throws Exception {
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
		        return uploadFile.getId();
		     }
		  } catch (Exception ex) {
		    throw ex;
		  }
		  return null;
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
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			driveService.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);

			java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
			String base64 =  new String(encoder.encode(outputStream.toByteArray()));
			item.setBase64String(base64);
			
			for(DocTypeMaster docType:allDocType) {
				if(file.getName().equals(String.valueOf(docType.getId()))) {
					item.setDocTypeId(docType.getId());
					item.setDocTypeDesc(docType.getFileDesc());
					break;
				}
			}
			listFileItem.add(item);
			
/*			System.out.println("base64 for file name"+file.getName()+"="+base64);
			if(file.getName().contains("3-mybanner")) {
				java.io.File tfile = new java.io.File( "C:\\Users\\test\\New folder\\abc.txt" );
				byte[] bytes = Base64.decodeBase64(base64 );
				org.apache.commons.io.FileUtils.writeByteArrayToFile( tfile, bytes );
			}
			if(file.getName().contains("SAMPLE")) {
				java.io.File tfile = new java.io.File( "C:\\Users\\Desktop\\test\\New folder\\abc.pdf" );
				byte[] bytes = Base64.decodeBase64(base64 );
				org.apache.commons.io.FileUtils.writeByteArrayToFile( tfile, bytes );
			}*/
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
		        .setFields("nextPageToken, files(id, name)")
		        .execute();
		  
		  return result.getFiles();
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



	
	

}

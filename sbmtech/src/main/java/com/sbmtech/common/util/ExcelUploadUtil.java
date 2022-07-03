package com.sbmtech.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.ContactDTO;
import com.sbmtech.dto.ExcelNewUserDTO;
import com.sbmtech.payload.request.ProfileRequest;

public class ExcelUploadUtil {
	


	public static ExcelNewUserDTO getCellValue(XSSFCell cd,ExcelNewUserDTO excelDTO) {
		
		switch(cd.getColumnIndex()) {
			case 1:
				excelDTO.setEmail(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 2:
				excelDTO.setMemberCategory(CommonUtil.getIntValofObject(cd.toString()));
				break;
			case 7:
				excelDTO.setFirstname(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 8:
				excelDTO.getPersonDetails().setMiddeName(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 9:
				excelDTO.setLastname(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 10:
				excelDTO.getPersonDetails().setDob(CommonUtil.getFormattedDatePattern(CommonUtil.getStringValofObject(cd.toString()), CommonConstants.DATE_dd_MMM_yyyy));
				break;	
			case 11:
				excelDTO.getPersonDetails().setBirthPlace(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 12:
				excelDTO.getPersonDetails().setMotherName(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 13:
				excelDTO.getPersonDetails().setFatherName(CommonUtil.getStringValofObject(cd.toString()));
				break;
			case 14:
				excelDTO.getPersonDetails().setBloodGroup(CommonUtil.getStringValofObject(cd.toString()));
				break;	
			case 15:
				excelDTO.getPersonDetails().setDaughter(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue());
				break;	
			case 16:
				excelDTO.getPersonDetails().setSon(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue());
				break;
			case 17:
				excelDTO.getPersonDetails().setAsserts(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).longValue());
				break;
			case 18:
				excelDTO.getPersonDetails().setAnnualIncome(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).longValue());
				break;	
				
				//contact Dto 1
			case 19:
				List<ContactDTO> contactDtoList=new ArrayList<ContactDTO>();
				ContactDTO contactDto=new ContactDTO();
				contactDtoList.add(contactDto);
				excelDTO.setContactDetails(contactDtoList);
				break;	
				
			case 20:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty()) {
					excelDTO.getContactDetails().get(0).setContactType(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue());
				}
				break;
			case 21:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setLatitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 22:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setLongitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 23:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setFlatNo(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;		
			case 24:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setShed(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 25:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setBuilding(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 26:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setStreet(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 27:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setArea(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 28:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 29:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setCountry(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 30:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setTaluk(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 31:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setDistrict(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 32:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setPinCode(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;
			case 33:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 34:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setPoBox(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 35:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setPhone(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 36:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setFax(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 37:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setMobileNo(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 38:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setEmailSale(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 39:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setEmailShop(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 40:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(0)!=null) {
					excelDTO.getContactDetails().get(0).setOwnerShip(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
				
				
				//contact Dto 2
			case 41:{
				
				List<ContactDTO> contactDtoList2=excelDTO.getContactDetails();
				ContactDTO contactDto2=new ContactDTO();
				if(contactDtoList2!=null) {
					contactDtoList2.add(contactDto2);
					excelDTO.setContactDetails(contactDtoList2);
				}
				break;	
			}
			case 42:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty()) {
					excelDTO.getContactDetails().get(1).setContactType(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue());
				}
				break;
			case 43:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setLatitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 44:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setLongitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 45:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setFlatNo(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;		
			case 46:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setShed(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 47:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setBuilding(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 48:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setStreet(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 49:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setArea(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 50:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 51:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setCountry(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 52:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setTaluk(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 53:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setDistrict(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 54:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setPinCode(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;
			case 55:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 56:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setPoBox(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 57:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setPhone(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 58:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setFax(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 59:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setMobileNo(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 60:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setEmailSale(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 61:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setEmailShop(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 62:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(1)!=null) {
					excelDTO.getContactDetails().get(1).setOwnerShip(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
				
				//contact Dto 3
			case 63:{
				
				List<ContactDTO> contactDtoList3=excelDTO.getContactDetails();
				ContactDTO contactDto3=new ContactDTO();
				if(contactDtoList3!=null) {
					contactDtoList3.add(contactDto3);
					excelDTO.setContactDetails(contactDtoList3);
				}
				break;	
			}
			case 64:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty()) {
					excelDTO.getContactDetails().get(2).setContactType(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue());
				}
				break;
			case 65:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setLatitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 66:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setLongitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 67:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setFlatNo(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;		
			case 68:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setShed(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 69:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setBuilding(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 70:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setStreet(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 71:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setArea(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 72:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 73:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setCountry(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 74:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setTaluk(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 75:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setDistrict(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 76:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setPinCode(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;
			case 77:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 78:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setPoBox(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 79:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setPhone(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 80:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setFax(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 81:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setMobileNo(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 82:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setEmailSale(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 83:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setEmailShop(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 84:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(2)!=null) {
					excelDTO.getContactDetails().get(2).setOwnerShip(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			
				
				//contact Dto 4
			case 85:{
				
				List<ContactDTO> contactDtoList4=excelDTO.getContactDetails();
				ContactDTO contactDto4=new ContactDTO();
				if(contactDtoList4!=null) {
					contactDtoList4.add(contactDto4);
					excelDTO.setContactDetails(contactDtoList4);
				}
				break;	
			}
			case 86:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty()) {
					excelDTO.getContactDetails().get(3).setContactType(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue());
				}
				break;
			case 87:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setLatitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 88:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setLongitude(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 89:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setFlatNo(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;		
			case 90:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setShed(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 91:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setBuilding(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 92:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setStreet(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 93:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setArea(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 94:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 95:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setCountry(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 96:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setTaluk(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 97:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setDistrict(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 98:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setPinCode(""+(Double.valueOf(CommonUtil.getDoubleValofObject(cd.toString())).intValue()));
				}
				break;
			case 99:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setState(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;	
			case 100:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setPoBox(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 101:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setPhone(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 102:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setFax(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 103:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setMobileNo(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 104:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setEmailSale(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 105:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setEmailShop(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;
			case 106:
				if(excelDTO.getContactDetails()!=null && !excelDTO.getContactDetails().isEmpty() 
						&& excelDTO.getContactDetails().get(3)!=null) {
					excelDTO.getContactDetails().get(3).setOwnerShip(CommonUtil.getStringValofObject(cd.toString()));
				}
				break;		
		}

		return excelDTO;
	}

	public static ProfileRequest removeEmptyContactType(ProfileRequest profileRequest) {
		List<ContactDTO> contactDetails=profileRequest.getContactDetails();
		/*for(int i=0;i<contactDetails.size();i++) {
			ContactDTO ct=contactDetails.get(i);
			System.out.println("ct type="+ct.getContactType());
			if((ct.getContactType()!=null && ct.getContactType()==0) || ct.getContactType()==null) {
				contactDetails.remove(ct);
			}
		}*/
		contactDetails.removeIf(x->(x.getContactType()!=null && x.getContactType()==0));
		for(int i=0;i<contactDetails.size();i++) {
			ContactDTO ct=contactDetails.get(i);
			//System.out.println("ct type="+ct.getContactType());
			if((ct.getContactType()!=null && ct.getContactType()==0) || ct.getContactType()==null) {
				contactDetails.remove(ct);
			}
		}
		return profileRequest;
	}


}

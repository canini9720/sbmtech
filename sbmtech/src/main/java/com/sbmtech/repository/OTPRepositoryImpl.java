package com.sbmtech.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.model.Otp;

@Repository
public class OTPRepositoryImpl extends JdbcCommonDao implements OTPRespositoryCustom  {

	
	@Override
	public Optional<OtpDTO> saveOtp(Long userId, Integer otpCode,String email, String flowType)throws Exception {
		StoredProcedureQuery qry = this.getEm().createStoredProcedureQuery("sp_send_otp");
	
		qry.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
		qry.registerStoredProcedureParameter("p_otp_code", Integer.class, ParameterMode.IN);
		qry.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
		qry.registerStoredProcedureParameter("p_flow_type", String.class, ParameterMode.IN);
		qry.setParameter("p_user_id",userId)
			.setParameter("p_otp_code", otpCode)
			.setParameter("p_email", email)
			.setParameter("p_flow_type",flowType);
		List lists = qry.getResultList();
		OtpDTO otp=null;
		if(lists != null && !lists.isEmpty()){
			otp=new OtpDTO();
			Object[] result = (Object[])lists.get(0);
			otp.setVerificationId(CommonUtil.getLongValofObject(result[0]));
			otp.setUserId(CommonUtil.getLongValofObject(result[1]));
			otp.setOtpCode(CommonUtil.getIntValofObject(result[2]));
			otp.setEmail(CommonUtil.getStringValofObject(result[3]));
		}
		Optional<OtpDTO> opt = Optional.ofNullable(otp);	
		return opt;
	}

	@Override
	public Boolean validateOtp(Long verificationId,Integer otpCode) throws Exception {
		StoredProcedureQuery qry = this.getEm().createStoredProcedureQuery("sp_validate_otp");
		
		qry.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
		qry.registerStoredProcedureParameter("p_user_otp", Integer.class, ParameterMode.IN);
		qry.registerStoredProcedureParameter("p_result", Integer.class, ParameterMode.OUT);
		
		qry.setParameter("p_id",verificationId)
			.setParameter("p_user_otp", otpCode);
		
		 qry.execute();
		Integer result=(Integer)qry.getOutputParameterValue("p_result");
		if(result!=null && result==CommonConstants.INT_ONE){
			return true;
		}
		return false;
	}

	@Override
	public Integer getUserType(Long verificationId) throws Exception {
		StoredProcedureQuery qry = this.getEm().createStoredProcedureQuery("sp_get_otp_usertype");
		
		qry.registerStoredProcedureParameter("p_verification_id", Long.class, ParameterMode.IN);
		qry.registerStoredProcedureParameter("p_user_type", Integer.class, ParameterMode.OUT);
		
		qry.setParameter("p_verification_id",verificationId);
		
		 qry.execute();
		Integer userType=(Integer)qry.getOutputParameterValue("p_user_type");
		
		return userType;
	}

}

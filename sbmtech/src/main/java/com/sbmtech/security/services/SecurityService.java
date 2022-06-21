package com.sbmtech.security.services;

import org.springframework.stereotype.Service;

@Service("securityService")
class SecurityService {

    public String getMember(){
        return "MEMBER";
    }
    public String getGroup(){
        return "GROUP";
    }
    public String getCompany(){
        return "COMPANY";
    }
    public String getAdmin(){
        return "ADMIN";
    }
    
    
    
}
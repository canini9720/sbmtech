package com.sbmtech.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("mail")
@Setter
@Getter
public class MailProperties {

	 private String host;
	 private String port;
	 private String username;
	 private String password;
	 private String auth;
	 private String starttls;
}

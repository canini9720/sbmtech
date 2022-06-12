package com.sbmtech.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.sbmtech.service.impl.AppSystemPropImpl;

@Configuration
@DependsOn("AppSystemProp")
public class VelocityEngineConfig {

	
	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException{
		String emailTemplatePath = AppSystemPropImpl.props.get("email.templatePath");
		Properties props = new Properties();
		props.put(RuntimeConstants.RESOURCE_LOADER, "file");
		props.put(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, emailTemplatePath);
		VelocityEngine velocityEngine = new VelocityEngine(props);
		velocityEngine.init();
		return velocityEngine;
		
	}
}

package com.sbmtech.security.jwt;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		logger.error("Unauthorized error: {}", authException.getMessage());
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("Access Denied... Forbidden");
		/*response.setStatus(403);
		response.getWriter().write(JsonBuilder //my util class for creating json strings
	                .put("status", 403)
	                .put("message", "Access denied")
	                .build());*/
	}
}
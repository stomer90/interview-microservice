package com.stormer.authenservice.securities.jwt;

import com.stormer.authenservice.constants.AuthenConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnAuthorizedHandler implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnAuthorizedHandler.class);

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        LOGGER.error("Unauthorized error. Message - {}", e.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String authHeader = request.getHeader(AuthenConstants.AUTHORIZATION);
        String errorMsg = "Invalid username/password";
        if(authHeader != null) {
            errorMsg = AuthenConstants.INVALID_TOKEN;
        }

        response.getOutputStream().println("{ \"error\": \"" + errorMsg + "\" }");
    }
}

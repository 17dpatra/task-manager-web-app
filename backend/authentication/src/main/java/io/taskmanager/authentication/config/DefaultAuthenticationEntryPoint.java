package io.taskmanager.authentication.config;

import io.taskmanager.authentication.exception.TokenFilterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error("Unauthorized attempt to access resource", authException);
        String message = authException.getMessage();
        if (authException instanceof TokenFilterException) {
            message = authException.getCause().getMessage();
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }
}

package com.project.orthodonticclinic.security.handler;

import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.exception.ErrorResponse;
import com.project.orthodonticclinic.util.JsonMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private static final String RESPONSE_CONTENT_TYPE = "application/json";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        ErrorResponse errorResponse = new ErrorResponse(Error.ACCESS_DENIED.getMessage());

        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(Error.ACCESS_DENIED.getHttpStatus().value());
        response.getWriter().write(JsonMapper.convertObjectToJson(errorResponse));
    }
}

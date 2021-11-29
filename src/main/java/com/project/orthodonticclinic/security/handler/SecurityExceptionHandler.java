package com.project.orthodonticclinic.security.handler;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.ErrorResponse;
import com.project.orthodonticclinic.util.FeedbackMessage;
import com.project.orthodonticclinic.util.JsonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityExceptionHandler extends OncePerRequestFilter {

    private static final String RESPONSE_CONTENT_TYPE = "application/json";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ApplicationException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getError().getMessage());

            response.setStatus(ex.getError().getHttpStatus().value());
            response.getWriter().write(JsonMapper.convertObjectToJson(errorResponse));
        } catch (RuntimeException ex) {
            System.out.println("Ex: " + ex.getMessage());
            FeedbackMessage feedbackMessage = new FeedbackMessage(ex.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(JsonMapper.convertObjectToJson(feedbackMessage));
        } finally {
            response.setContentType(RESPONSE_CONTENT_TYPE);
        }
    }
}

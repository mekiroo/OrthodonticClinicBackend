package com.project.orthodonticclinic.security.jwt;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateJwtToken(Authentication authentication);

    void validateJwt(String jwt);

    Long getUserIdFromJwt(String jwt);

    String getUsernameFromJwt(String jwt);

    String getRoleFromJwt(String jwt);
}

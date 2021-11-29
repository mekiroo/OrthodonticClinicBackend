package com.project.orthodonticclinic.security.jwt;

import com.project.orthodonticclinic.user.account.RoleName;
import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.security.SecurityAccount;
import com.project.orthodonticclinic.user.User;
import com.project.orthodonticclinic.user.employee.EmployeeRepository;
import com.project.orthodonticclinic.user.patient.PatientRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public String generateJwtToken(Authentication authentication) {
        SecurityAccount securityAccount = (SecurityAccount) authentication.getPrincipal();
        Map<String, Object> claims = generateClaimsForSecurityAccount(securityAccount);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(securityAccount.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    @Override
    public void validateJwt(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken);
        } catch (RuntimeException ex) {
            throw new ApplicationException(Error.INVALID_JWT);
        }
    }

    @Override
    public Long getUserIdFromJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwt).getBody();
        return claims.get("user_id", Long.class);
    }

    @Override
    public String getUsernameFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwt).getBody().getSubject();
    }

    @Override
    public String getRoleFromJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwt).getBody();
        return claims.get("role", String.class);
    }

    private Map<String, Object> generateClaimsForSecurityAccount(SecurityAccount securityAccount) {
        Map<String, Object> claims = new HashMap<>();
        String username = securityAccount.getUsername();
        User user = null;

        if (securityAccount.getAccount().getRole().getName().equals(RoleName.PATIENT)) {
            user = patientRepository.findByAccountUsername(username).orElseThrow();
        } else if (securityAccount.getAccount().getRole().getName().equals(RoleName.EMPLOYEE)) {
            user = employeeRepository.findByAccountUsername(username).orElseThrow();
        }

        if (user != null) {
            claims.put("first_name", user.getFirstName());
            claims.put("last_name", user.getLastName());
            claims.put("user_id", user.getId());
        }
        claims.put("role", securityAccount.getAccount().getRole().getName());
        return claims;
    }
}

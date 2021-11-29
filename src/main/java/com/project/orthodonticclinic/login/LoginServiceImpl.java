package com.project.orthodonticclinic.login;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.login.payload.JwtResponse;
import com.project.orthodonticclinic.login.payload.LoginRequest;
import com.project.orthodonticclinic.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticateUser(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    private Authentication authenticateUser(LoginRequest loginRequest) {
        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        try {
            // If username or password is incorrect, authenticate() method will throw an exception
            return authenticationManager.authenticate(authToken);
        } catch (AuthenticationException ex) {
            throw new ApplicationException(Error.BAD_LOGIN_CREDENTIALS);
        }
    }
}

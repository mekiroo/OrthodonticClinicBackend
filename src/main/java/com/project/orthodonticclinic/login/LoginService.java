package com.project.orthodonticclinic.login;

import com.project.orthodonticclinic.login.payload.JwtResponse;
import com.project.orthodonticclinic.login.payload.LoginRequest;

public interface LoginService {

    JwtResponse login(LoginRequest loginRequest);
}

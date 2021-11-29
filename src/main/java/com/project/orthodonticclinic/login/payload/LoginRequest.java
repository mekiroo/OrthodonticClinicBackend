package com.project.orthodonticclinic.login.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {

    @NotBlank(message = "username can not be blank")
    private String username;
    @NotBlank(message = "password can not be blank")
    private String password;
}

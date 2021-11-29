package com.project.orthodonticclinic.user.patient.payload;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class CreatePatientRequest {

    @NotBlank(message = "Username can not be blank")
    @Size(min = 6, max = 30, message = "Username length must be between 6 to 30 characters")
    private String username;
    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, max = 45, message = "Password length must be between 6 to 45 characters")
    private String password;
    @NotBlank(message = "First name can not be blank")
    private String firstName;
    @NotBlank(message = "Last name can not be blank")
    private String lastName;
    @NotBlank(message = "Email can not be blank")
    @Size(min = 3, max = 30, message = "Email length must be between 3 to 30 characters")
    @Email(message = "Not valid email format")
    private String email;
    @NotBlank(message = "Phone number can not be blank")
    @Pattern(regexp = "^\\d{9}$", message = "Phone number must consist of 9 digits")
    private String phoneNumber;
    @NotBlank(message = "PESEL can not be blank")
    @Pattern(regexp = "^\\d{11}$", message = "PESEL must consist of 11 digits")
    private String pesel;
}

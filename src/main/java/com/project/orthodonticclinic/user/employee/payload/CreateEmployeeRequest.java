package com.project.orthodonticclinic.user.employee.payload;

import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class CreateEmployeeRequest {

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
    @NotNull(message = "Position id can not be blank")
    private Long positionId;
}

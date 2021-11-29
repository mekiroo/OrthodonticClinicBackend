package com.project.orthodonticclinic.user.employee.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long positionId;
    private String positionName;
}

package com.project.orthodonticclinic.user.patient.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pesel;
}

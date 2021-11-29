package com.project.orthodonticclinic.user.patient.util;

import com.project.orthodonticclinic.user.patient.Patient;
import com.project.orthodonticclinic.user.patient.payload.CreatePatientRequest;
import com.project.orthodonticclinic.user.patient.payload.PatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientMapperImpl implements PatientMapper {

    @Override
    public PatientResponse mapToPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .pesel(patient.getPesel())
                .build();
    }

    @Override
    public Patient mapFromCreatePatientRequest(CreatePatientRequest request) {
        var patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setPesel(request.getPesel());
        return patient;
    }
}

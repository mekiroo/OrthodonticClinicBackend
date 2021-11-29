package com.project.orthodonticclinic.user.patient.util;

import com.project.orthodonticclinic.user.patient.Patient;
import com.project.orthodonticclinic.user.patient.payload.CreatePatientRequest;
import com.project.orthodonticclinic.user.patient.payload.PatientResponse;

public interface PatientMapper {

    PatientResponse mapToPatientResponse(Patient patient);

    Patient mapFromCreatePatientRequest(CreatePatientRequest request);
}

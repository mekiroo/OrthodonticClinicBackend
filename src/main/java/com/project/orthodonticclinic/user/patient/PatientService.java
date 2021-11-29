package com.project.orthodonticclinic.user.patient;

import com.project.orthodonticclinic.user.patient.payload.CreatePatientRequest;
import com.project.orthodonticclinic.user.patient.payload.PatientResponse;

import java.util.List;

public interface PatientService {

    PatientResponse getPatientById(Long patientId);

    List<PatientResponse> getAllPatients();

    PatientResponse createPatient(CreatePatientRequest request);
}

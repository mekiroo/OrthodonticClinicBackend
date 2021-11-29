package com.project.orthodonticclinic.visit.service;

import com.project.orthodonticclinic.visit.payload.CreateVisitRequest;
import com.project.orthodonticclinic.visit.payload.EditVisitRequest;
import com.project.orthodonticclinic.visit.payload.VisitResponse;

import java.time.LocalDate;
import java.util.List;

public interface VisitService {

    List<VisitResponse> getPatientVisits(Long patientId);

    List<VisitResponse> getPatientVisitsByStatus(Long patientId, String status);

    List<VisitResponse> getEmployeeVisits(Long employeeId);

    List<VisitResponse> getEmployeeVisitsByDate(Long employeeId, LocalDate date);

    List<VisitResponse> getEmployeeVisitsByStatus(Long employeeId, String visitStatusName);

    List<VisitResponse> getEmployeeVisitsByDateAndStatus(Long employeeId, LocalDate date, String status);

    VisitResponse getVisitById(Long id);

    VisitResponse createVisit(CreateVisitRequest request);

    VisitResponse editVisitById(Long id, EditVisitRequest request);
}

package com.project.orthodonticclinic.visit.service;

import com.project.orthodonticclinic.visit.payload.VisitTimeResponse;

import java.time.LocalDate;
import java.util.List;

public interface VisitTimeService {

    List<VisitTimeResponse> getPossibleVisitTimes(Long employeeId, Long patientId, Long visitTypeId, LocalDate date);
}

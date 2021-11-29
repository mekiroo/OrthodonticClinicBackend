package com.project.orthodonticclinic.visit.util;

import com.project.orthodonticclinic.user.employee.EmployeeRepository;
import com.project.orthodonticclinic.user.patient.PatientRepository;
import com.project.orthodonticclinic.visit.Visit;
import com.project.orthodonticclinic.visit.payload.CreateVisitRequest;
import com.project.orthodonticclinic.visit.payload.VisitResponse;
import com.project.orthodonticclinic.visit.payload.VisitTimeResponse;
import com.project.orthodonticclinic.visit.visitstatus.EVisitStatusName;
import com.project.orthodonticclinic.visit.visitstatus.VisitStatusRepository;
import com.project.orthodonticclinic.visittype.VisitTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisitMapperImpl implements VisitMapper {

    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final VisitTypeRepository visitTypeRepository;
    private final VisitStatusRepository visitStatusRepository;

    @Override
    public VisitResponse mapToVisitResponse(Visit visit) {
        return VisitResponse.builder()
                .id(visit.getId())
                .visitTypeId(visit.getVisitType().getId())
                .visitTypeName(visit.getVisitType().getName())
                .visitStatus(visit.getVisitStatus().getName().name())
                .patientId(visit.getPatient().getId())
                .patientFirstName(visit.getPatient().getFirstName())
                .patientLastName(visit.getPatient().getLastName())
                .employeeId(visit.getEmployee().getId())
                .employeeFirstName(visit.getEmployee().getFirstName())
                .employeeLastName(visit.getEmployee().getLastName())
                .description(visit.getDescription())
                .date(visit.getDate().toString())
                .startTime(visit.getStartTime().toString())
                .endTime(visit.getEndTime().toString())
                .build();
    }

    @Override
    public Visit mapFromCreateVisitRequest(CreateVisitRequest request) {
        var visit = new Visit();
        var patient = patientRepository.findById(request.getPatientId()).orElseThrow();
        var employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow();
        var visitType = visitTypeRepository.findById(request.getVisitTypeId()).orElseThrow();
        var visitStatus = visitStatusRepository.findByName(EVisitStatusName.WAITING).orElseThrow();

        visit.setPatient(patient);
        visit.setEmployee(employee);
        visit.setVisitType(visitType);
        visit.setVisitStatus(visitStatus);
        visit.setDescription(request.getDescription());
        visit.setDate(request.getDate());
        visit.setStartTime(request.getStartTime());
        visit.setEndTime(request.getStartTime().plusMinutes(visitType.getDuration()));
        return visit;
    }

    @Override
    public VisitTimeResponse mapFromTimeIntervalToVisitTimeResponse(TimeInterval timeInterval) {
        return new VisitTimeResponse(timeInterval.getStartTime().toString(), timeInterval.getEndTime().toString());
    }
}

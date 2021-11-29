package com.project.orthodonticclinic.visit.service;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.user.employee.EmployeeRepository;
import com.project.orthodonticclinic.user.patient.PatientRepository;
import com.project.orthodonticclinic.visit.Visit;
import com.project.orthodonticclinic.visit.VisitRepository;
import com.project.orthodonticclinic.visit.payload.CreateVisitRequest;
import com.project.orthodonticclinic.visit.payload.EditVisitRequest;
import com.project.orthodonticclinic.visit.payload.VisitResponse;
import com.project.orthodonticclinic.visit.util.VisitMapper;
import com.project.orthodonticclinic.visit.visitstatus.EVisitStatusName;
import com.project.orthodonticclinic.visit.visitstatus.VisitStatus;
import com.project.orthodonticclinic.visit.visitstatus.VisitStatusRepository;
import com.project.orthodonticclinic.visittype.VisitType;
import com.project.orthodonticclinic.visittype.VisitTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitStatusRepository visitStatusRepository;
    private final VisitTypeRepository visitTypeRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final VisitMapper visitMapper;

    @Override
    public VisitResponse getVisitById(Long id) {
        var visit = getVisitEntityById(id);
        return visitMapper.mapToVisitResponse(visit);
    }

    private Visit getVisitEntityById(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_VISIT_ID));
    }

    @Override
    public List<VisitResponse> getPatientVisits(Long patientId) {
        checkIfPatientExistsById(patientId);
        return visitRepository.findAllByPatientId(patientId).stream()
                .map(visitMapper::mapToVisitResponse)
                .collect(Collectors.toList());
    }

    private void checkIfPatientExistsById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_PATIENT_ID);
        }
    }

    @Override
    public List<VisitResponse> getPatientVisitsByStatus(Long patientId, String visitStatusName) {
        checkIfPatientExistsById(patientId);
        var enumVisitStatusName = tryGetVisitStatusNameFromString(visitStatusName);
        return visitRepository.findAllByPatientIdAndVisitStatusName(patientId, enumVisitStatusName).stream()
                .map(visitMapper::mapToVisitResponse)
                .collect(Collectors.toList());
    }

    private EVisitStatusName tryGetVisitStatusNameFromString(String visitStatusName) {
        try {
            return EVisitStatusName.valueOf(visitStatusName);
        } catch (IllegalArgumentException ex) {
            throw new ApplicationException(Error.INCORRECT_VISIT_STATUS_NAME);
        }
    }

    @Override
    public List<VisitResponse> getEmployeeVisits(Long employeeId) {
        checkIfEmployeeExistsById(employeeId);
        return visitRepository.findAllByEmployeeId(employeeId).stream()
                .map(visitMapper::mapToVisitResponse)
                .collect(Collectors.toList());
    }

    private void checkIfEmployeeExistsById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_EMPLOYEE_ID);
        }
    }

    @Override
    public List<VisitResponse> getEmployeeVisitsByDate(Long employeeId, LocalDate date) {
        checkIfEmployeeExistsById(employeeId);
        return visitRepository.findAllByEmployeeIdAndDate(employeeId, date).stream()
                .map(visitMapper::mapToVisitResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitResponse> getEmployeeVisitsByStatus(Long employeeId, String visitStatusName) {
        checkIfEmployeeExistsById(employeeId);
        var enumVisitStatusName = tryGetVisitStatusNameFromString(visitStatusName);
        return visitRepository.findAllByEmployeeIdAndVisitStatusName(employeeId, enumVisitStatusName).stream()
                .map(visitMapper::mapToVisitResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitResponse> getEmployeeVisitsByDateAndStatus(Long employeeId, LocalDate date, String visitStatusName) {
        checkIfEmployeeExistsById(employeeId);
        var enumVisitStatusName = tryGetVisitStatusNameFromString(visitStatusName);
        return visitRepository.findAllByEmployeeIdAndDateAndVisitStatusName(employeeId, date, enumVisitStatusName).stream()
                .map(visitMapper::mapToVisitResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VisitResponse createVisit(CreateVisitRequest request) {
        validateCreateVisitRequest(request);
        var visitType = getVisitTypeEntityById(request.getVisitTypeId());
        var endTime = request.getStartTime().plusMinutes(visitType.getDuration());
        checkIfPatientHasVisitOnDate(request.getPatientId(), request.getDate(), request.getStartTime(), endTime);
        checkIfEmployeeHasVisitOnDate(request.getEmployeeId(), request.getDate(), request.getStartTime(), endTime);
        var visit = visitMapper.mapFromCreateVisitRequest(request);
        visitRepository.save(visit);
        return visitMapper.mapToVisitResponse(visit);
    }

    private void validateCreateVisitRequest(CreateVisitRequest request) {
        checkIfPatientExistsById(request.getPatientId());
        checkIfEmployeeExistsById(request.getEmployeeId());
        checkIfVisitTypeExistsById(request.getVisitTypeId());
        checkIfEmployeeCanPerformVisit(request.getEmployeeId(), request.getVisitTypeId());
    }

    public void checkIfVisitTypeExistsById(Long id) {
        if (!visitTypeRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_VISIT_TYPE_ID);
        }
    }

    private VisitType getVisitTypeEntityById(Long visitTypeId) {
        return visitTypeRepository.findById(visitTypeId).orElseThrow(() -> {
            throw new ApplicationException(Error.INCORRECT_VISIT_TYPE_ID);
        });
    }

    private void checkIfEmployeeCanPerformVisit(Long employeeId, Long visitTypeId) {
        var employee = employeeRepository.findById(employeeId).orElseThrow();
        var visitType = visitTypeRepository.findById(visitTypeId).orElseThrow();

        if (!employee.getPosition().equals(visitType.getPosition())) {
            throw new ApplicationException(Error.VISIT_CAN_NOT_BE_CREATED_INCORRECT_EMPLOYEE_AND_VISIT_TYPE);
        }
    }

    private void checkIfPatientHasVisitOnDate(Long patientId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Visit> patientVisits = visitRepository.findAllByPatientIdAndDate(patientId, date);
        if (isAnyVisitAlreadyBooked(patientVisits, startTime, endTime)) {
            throw new ApplicationException(Error.PATIENT_ALREADY_HAS_VISIT);
        }
    }

    private void checkIfEmployeeHasVisitOnDate(Long employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Visit> employeeVisits = visitRepository.findAllByEmployeeIdAndDate(employeeId, date);
        if (isAnyVisitAlreadyBooked(employeeVisits, startTime, endTime)) {
            throw new ApplicationException(Error.EMPLOYEE_ALREADY_HAS_VISIT);
        }
    }

    private boolean isAnyVisitAlreadyBooked(List<Visit> userVisits, LocalTime startTime, LocalTime endTime) {
        for (Visit visit : userVisits) {
            // If passed startTime is between start time and end time (not inclusive) of the visit
            if (startTime.isAfter(visit.getStartTime()) && startTime.isBefore(visit.getEndTime())) {
                return true;
            }
            // If passed endTime is between start time and end time (not inclusive) of the visit
            if (endTime.isAfter(visit.getStartTime()) && endTime.isBefore(visit.getEndTime())) {
                return true;
            }
            // If passed startTime and endTime are between start time and end time (not inclusive) of the visit
            if (startTime.isBefore(visit.getStartTime()) && endTime.isAfter(visit.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public VisitResponse editVisitById(Long visitId, EditVisitRequest request) {
        var visit = getVisitEntityById(visitId);
        var enumVisitStatusName = tryGetVisitStatusNameFromString(request.getVisitStatus());
        var visitStatus = getVisitStatusEntityByName(enumVisitStatusName);
        visit.setVisitStatus(visitStatus);
        visit.setDescription(request.getDescription());
        return visitMapper.mapToVisitResponse(visit);
    }

    private VisitStatus getVisitStatusEntityByName(EVisitStatusName visitStatusName) {
        return visitStatusRepository.findByName(visitStatusName)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_VISIT_STATUS_ID));
    }
}

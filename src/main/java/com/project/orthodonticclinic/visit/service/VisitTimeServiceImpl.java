package com.project.orthodonticclinic.visit.service;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.user.employee.EmployeeRepository;
import com.project.orthodonticclinic.user.patient.PatientRepository;
import com.project.orthodonticclinic.visit.Visit;
import com.project.orthodonticclinic.visit.VisitRepository;
import com.project.orthodonticclinic.visit.payload.VisitTimeResponse;
import com.project.orthodonticclinic.visit.util.TimeInterval;
import com.project.orthodonticclinic.visit.util.VisitMapper;
import com.project.orthodonticclinic.visit.visitstatus.EVisitStatusName;
import com.project.orthodonticclinic.visittype.VisitType;
import com.project.orthodonticclinic.visittype.VisitTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitTimeServiceImpl implements VisitTimeService {

    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(16, 0);
    private static final long VISIT_TIME_INTERVAL = 30;

    private final VisitRepository visitRepository;
    private final VisitTypeRepository visitTypeRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final VisitMapper visitMapper;

    @Override
    @Transactional
    public List<VisitTimeResponse> getPossibleVisitTimes(Long employeeId, Long patientId, Long visitTypeId, LocalDate date) {
        checkIfEmployeeExistsById(employeeId);
        checkIfPatientExistsById(patientId);
        checkIfVisitTypeExistsById(visitTypeId);
        var visits = getEmployeeAndPatientVisitsSortedByStarTime(employeeId, patientId, date);
        visits = deleteVisitsWithCanceledStatus(visits);
        var freeTimes = getFreeTimesBetweenVisits(visits);
        var visitType = getVisitTypeEntityById(visitTypeId);
        var possibleVisitTimes = calculatePossibleVisitTimes(freeTimes, visitType.getDuration());

        return possibleVisitTimes.stream()
                .map(visitMapper::mapFromTimeIntervalToVisitTimeResponse)
                .collect(Collectors.toList());
    }

    private void checkIfPatientExistsById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_PATIENT_ID);
        }
    }

    private void checkIfEmployeeExistsById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_EMPLOYEE_ID);
        }
    }

    private void checkIfVisitTypeExistsById(Long id) {
        if (!visitTypeRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_VISIT_TYPE_ID);
        }
    }

    private List<Visit> getEmployeeAndPatientVisitsSortedByStarTime(Long employeeId, Long patientId, LocalDate date) {
        var visits = visitRepository.findAllByEmployeeIdAndDate(employeeId, date);
        visits.addAll(visitRepository.findAllByPatientIdAndDate(patientId, date));

        return visits.stream()
                .distinct()
                .sorted(Comparator.comparing(Visit::getStartTime))
                .collect(Collectors.toList());
    }

    private List<Visit> deleteVisitsWithCanceledStatus(List<Visit> visits) {
        return visits.stream()
                .filter(visit -> !visit.getVisitStatus().getName().equals(EVisitStatusName.CANCELED))
                .collect(Collectors.toList());
    }

    private List<TimeInterval> getFreeTimesBetweenVisits(List<Visit> visits) {
        List<TimeInterval> freeTimes = new ArrayList<>();
        if (visits.size() == 0) {
            freeTimes.add(new TimeInterval(OPENING_TIME, CLOSING_TIME));
            return freeTimes;
        }

        LocalTime tempEndTime = null;
        for (int i = 0; i < visits.size(); i++) {
            /* If start time of first visit is equal to OPENING_TIME, save end time of this visit
                and move to the next visit */
            if (i == 0 && visits.get(i).getStartTime().equals(OPENING_TIME)) {
                tempEndTime = visits.get(i).getEndTime();
                continue;
            } else if (i == 0 && !visits.get(i).getStartTime().equals(OPENING_TIME)) {
                tempEndTime = visits.get(i).getEndTime();
                freeTimes.add(new TimeInterval(OPENING_TIME, visits.get(i).getStartTime()));
                continue;
            }

            /* If start time of the current visit is equal to end time of the previous visit,
               save end time of this visit and move to the next visit */
            if (visits.get(i).getStartTime().equals(tempEndTime)) {
                tempEndTime = visits.get(i).getEndTime();
            } else {
                freeTimes.add(new TimeInterval(tempEndTime, visits.get(i).getStartTime()));
                tempEndTime = visits.get(i).getEndTime();
            }
        }
        // If end time of last visit is before CLOSING_TIME, calculate remaining time and add it to freeHours
        if (tempEndTime != null && tempEndTime.isBefore(CLOSING_TIME)) {
            freeTimes.add(new TimeInterval(tempEndTime, CLOSING_TIME));
        }

        return freeTimes;
    }

    private List<TimeInterval> calculatePossibleVisitTimes(List<TimeInterval> freeTimes, long visitDuration) {
        List<TimeInterval> possibleVisitTime = new ArrayList<>();
        for (TimeInterval freeTime : freeTimes) {
            long minutesBetween = Duration.between(freeTime.getStartTime(), freeTime.getEndTime()).toMinutes();
            long iterationNumber = minutesBetween / VISIT_TIME_INTERVAL;

            LocalTime tempStartTime = null;
            LocalTime tempEndTime = null;
            for (int i = 0; i < iterationNumber; i++) {
                if (i == 0) {
                    tempStartTime = freeTime.getStartTime();
                }
                tempEndTime = tempStartTime.plusMinutes(visitDuration);
                if (tempEndTime.isBefore(freeTime.getEndTime().plusSeconds(1))) {
                    possibleVisitTime.add(new TimeInterval(tempStartTime, tempEndTime));
                    tempStartTime = tempStartTime.plusMinutes(VISIT_TIME_INTERVAL);
                }
            }
        }
        return possibleVisitTime;
    }

    private VisitType getVisitTypeEntityById(Long id) {
        return visitTypeRepository.findById(id).orElseThrow(() -> {
            throw new ApplicationException(Error.INCORRECT_VISIT_TYPE_ID);
        });
    }
}

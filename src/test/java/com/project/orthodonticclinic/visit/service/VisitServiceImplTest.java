package com.project.orthodonticclinic.visit.service;

import com.ocadotechnology.gembus.test.Arranger;
import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.position.PositionTestHelper;
import com.project.orthodonticclinic.user.employee.Employee;
import com.project.orthodonticclinic.user.employee.EmployeeRepository;
import com.project.orthodonticclinic.user.patient.PatientRepository;
import com.project.orthodonticclinic.visit.Visit;
import com.project.orthodonticclinic.visit.VisitRepository;
import com.project.orthodonticclinic.visit.VisitTestHelper;
import com.project.orthodonticclinic.visit.payload.VisitResponse;
import com.project.orthodonticclinic.visit.util.VisitMapper;
import com.project.orthodonticclinic.visit.visitstatus.EVisitStatusName;
import com.project.orthodonticclinic.visit.visitstatus.VisitStatusRepository;
import com.project.orthodonticclinic.visittype.VisitType;
import com.project.orthodonticclinic.visittype.VisitTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {

    @Mock
    private VisitRepository visitRepository;
    @Mock
    private VisitStatusRepository visitStatusRepository;
    @Mock
    private VisitTypeRepository visitTypeRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private VisitMapper visitMapper;
    @InjectMocks
    private VisitServiceImpl visitServiceImpl;

    @Test
    void shouldThrowExceptionIfVisitDoesNotExistDuringGettingVisitById() {
        // given
        var visitId = Arranger.someLong();
        Mockito.when(visitRepository.findById(visitId))
                .thenReturn(Optional.empty());

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.getVisitById(visitId));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_VISIT_ID);
    }

    @Test
    void shouldReturnVisitResponseById() {
        // given
        var visitId = Arranger.someLong();
        var visit = Mockito.mock(Visit.class);
        var expectedVisitResponse = Mockito.mock(VisitResponse.class);
        Mockito.when(visitRepository.findById(visitId))
                .thenReturn(Optional.of(visit));
        Mockito.when(visitMapper.mapToVisitResponse(visit))
                .thenReturn(expectedVisitResponse);

        // when
        var result = visitServiceImpl.getVisitById(visitId);

        // then
        assertThat(result).isEqualTo(expectedVisitResponse);
    }

    @Test
    void shouldThrowExceptionIfPatientDoesNotExistDuringGettingPatientVisits() {
        // given
        var patientId = Arranger.someLong();
        Mockito.when(patientRepository.existsById(patientId))
                .thenReturn(false);

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.getPatientVisits(patientId));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_PATIENT_ID);
    }

    @Test
    void shouldReturnListOfVisitResponseByPatientId() {
        // given
        var patientId = Arranger.someLong();
        var visit1 = Mockito.mock(Visit.class);
        var visit2 = Mockito.mock(Visit.class);
        var expectedVisitResponse1 = Mockito.mock(VisitResponse.class);
        var expectedVisitResponse2 = Mockito.mock(VisitResponse.class);
        Mockito.when(patientRepository.existsById(patientId))
                .thenReturn(true);
        Mockito.when(visitRepository.findAllByPatientId(patientId))
                .thenReturn(List.of(visit1, visit2));
        Mockito.when(visitMapper.mapToVisitResponse(visit1))
                .thenReturn(expectedVisitResponse1);
        Mockito.when(visitMapper.mapToVisitResponse(visit2))
                .thenReturn(expectedVisitResponse2);

        // when
        var visitResponses = visitServiceImpl.getPatientVisits(patientId);

        // then
        Assertions.assertAll(
                () -> assertThat(visitResponses.get(0)).isEqualTo(expectedVisitResponse1),
                () -> assertThat(visitResponses.get(1)).isEqualTo(expectedVisitResponse2)
        );
    }

    @Test
    void shouldThrowExceptionIfPatientDoesNotExistDuringGettingPatientVisitsByStatus() {
        // given
        var patientId = Arranger.someLong();
        var status = EVisitStatusName.COMPLETED.name();
        Mockito.when(patientRepository.existsById(patientId))
                .thenReturn(false);

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.getPatientVisitsByStatus(patientId, status));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_PATIENT_ID);
    }

    @Test
    void shouldThrowExceptionIfVisitStatusDoesNotExistDuringGettingPatientVisitsByStatus() {
        // given
        var patientId = Arranger.someLong();
        var status = Arranger.someString();
        Mockito.when(patientRepository.existsById(patientId))
                .thenReturn(true);

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.getPatientVisitsByStatus(patientId, status));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_VISIT_STATUS_NAME);
    }

    @Test
    void shouldReturnListOfVisitResponsesByPatientIdAndStatus() {
        // given
        var patientId = Arranger.someLong();
        var enumStatus = EVisitStatusName.COMPLETED;
        var status = EVisitStatusName.COMPLETED.name();
        var visit1 = Mockito.mock(Visit.class);
        var visit2 = Mockito.mock(Visit.class);
        var expectedVisitResponse1 = Mockito.mock(VisitResponse.class);
        var expectedVisitResponse2 = Mockito.mock(VisitResponse.class);
        Mockito.when(patientRepository.existsById(patientId))
                .thenReturn(true);
        Mockito.when(visitRepository.findAllByPatientIdAndVisitStatusName(patientId, enumStatus))
                .thenReturn(List.of(visit1, visit2));
        Mockito.when(visitMapper.mapToVisitResponse(visit1))
                .thenReturn(expectedVisitResponse1);
        Mockito.when(visitMapper.mapToVisitResponse(visit2))
                .thenReturn(expectedVisitResponse2);

        // when
        var visitResponses = visitServiceImpl.getPatientVisitsByStatus(patientId, status);

        // then
        Assertions.assertAll(
                () -> assertThat(visitResponses.get(0)).isEqualTo(expectedVisitResponse1),
                () -> assertThat(visitResponses.get(1)).isEqualTo(expectedVisitResponse2)
        );
    }

    @Test
    void shouldThrowExceptionIfPatientDoesNotExistDuringCreatingVisit() {
        // given
        var request = VisitTestHelper.createCreateVisitRequest();
        Mockito.when(patientRepository.existsById(request.getPatientId()))
                .thenReturn(false);

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.createVisit(request));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_PATIENT_ID);
    }

    @Test
    void shouldThrowExceptionIfEmployeeDoesNotExistDuringCreatingVisit() {
        // given
        var request = VisitTestHelper.createCreateVisitRequest();
        Mockito.when(patientRepository.existsById(request.getPatientId()))
                .thenReturn(true);
        Mockito.when(employeeRepository.existsById(request.getEmployeeId()))
                .thenReturn(false);

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.createVisit(request));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_EMPLOYEE_ID);
    }

    @Test
    void shouldThrowExceptionIfVisitTypeDoesNotExistDuringCreatingVisit() {
        // given
        var request = VisitTestHelper.createCreateVisitRequest();
        Mockito.when(patientRepository.existsById(request.getPatientId()))
                .thenReturn(true);
        Mockito.when(employeeRepository.existsById(request.getEmployeeId()))
                .thenReturn(true);
        Mockito.when(visitTypeRepository.existsById(request.getVisitTypeId()))
                .thenReturn(false);

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.createVisit(request));
        assertThat(exception.getError()).isEqualTo(Error.INCORRECT_VISIT_TYPE_ID);
    }

    @Test
    void shouldThrowExceptionIfEmployeeCanNotPerformVisitDuringCreatingVisit() {
        // given
        var request = VisitTestHelper.createCreateVisitRequest();
        var employee = new Employee();
        var visitType = new VisitType();
        employee.setPosition(PositionTestHelper.createPosition());
        visitType.setPosition(PositionTestHelper.createPosition());
        Mockito.when(patientRepository.existsById(request.getPatientId()))
                .thenReturn(true);
        Mockito.when(employeeRepository.existsById(request.getEmployeeId()))
                .thenReturn(true);
        Mockito.when(visitTypeRepository.existsById(request.getVisitTypeId()))
                .thenReturn(true);
        Mockito.when(employeeRepository.findById(request.getEmployeeId()))
                .thenReturn(Optional.of(employee));
        Mockito.when(visitTypeRepository.findById(request.getVisitTypeId()))
                .thenReturn(Optional.of(visitType));

        // when
        // then
        var exception = Assertions.assertThrows(ApplicationException.class,
                () -> visitServiceImpl.createVisit(request));
        assertThat(exception.getError()).isEqualTo(Error.VISIT_CAN_NOT_BE_CREATED_INCORRECT_EMPLOYEE_AND_VISIT_TYPE);
    }
}

package com.project.orthodonticclinic.visit;

import com.project.orthodonticclinic.visit.visitstatus.EVisitStatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus")
    List<Visit> findAll();

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.patient.id = :patientId ORDER BY v.date, v.startTime")
    List<Visit> findAllByPatientId(Long patientId);

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.patient.id = :patientId AND v.date = :date ORDER BY v.startTime")
    List<Visit> findAllByPatientIdAndDate(Long patientId, LocalDate date);

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.patient.id = :patientId AND v.visitStatus.name = :visitStatusName ORDER BY v.date, v.startTime")
    List<Visit> findAllByPatientIdAndVisitStatusName(Long patientId, EVisitStatusName visitStatusName);

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.employee.id = :employeeId ORDER BY v.date, v.startTime")
    List<Visit> findAllByEmployeeId(Long employeeId);

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.employee.id = :employeeId AND v.date = :date ORDER BY v.startTime")
    List<Visit> findAllByEmployeeIdAndDate(Long employeeId, LocalDate date);

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.employee.id = :employeeId AND v.visitStatus.name = :visitStatusName ORDER BY v.date, v.startTime")
    List<Visit> findAllByEmployeeIdAndVisitStatusName(Long employeeId, EVisitStatusName visitStatusName);

    @Query("SELECT v FROM Visit v INNER JOIN FETCH v.patient INNER JOIN FETCH v.employee INNER JOIN FETCH v.visitType" +
            " INNER JOIN FETCH v.visitStatus WHERE v.employee.id = :employeeId AND v.date = :date AND" +
            " v.visitStatus.name = :visitStatusName ORDER BY v.date, v.startTime")
    List<Visit> findAllByEmployeeIdAndDateAndVisitStatusName(Long employeeId, LocalDate date, EVisitStatusName visitStatusName);
}

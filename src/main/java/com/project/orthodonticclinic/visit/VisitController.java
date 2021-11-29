package com.project.orthodonticclinic.visit;

import com.project.orthodonticclinic.visit.payload.CreateVisitRequest;
import com.project.orthodonticclinic.visit.payload.EditVisitRequest;
import com.project.orthodonticclinic.visit.payload.VisitResponse;
import com.project.orthodonticclinic.visit.payload.VisitTimeResponse;
import com.project.orthodonticclinic.visit.service.VisitService;
import com.project.orthodonticclinic.visit.service.VisitTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final VisitTimeService visitTimeService;

    @GetMapping("/api/visits/possible-visit-times")
    public List<VisitTimeResponse> getPossibleVisitTimes(@RequestParam Long employeeId,
                                                         @RequestParam Long patientId,
                                                         @RequestParam Long visitTypeId,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return visitTimeService.getPossibleVisitTimes(employeeId, patientId, visitTypeId, date);
    }

    @GetMapping("/api/patients/{id}/visits/{visitId}")
    @PreAuthorize("#id.equals(authentication.principal) || hasAnyRole('EMPLOYEE', 'ADMIN')")
    public VisitResponse getVisitById(@PathVariable Long id, @PathVariable Long visitId) {
        return visitService.getVisitById(visitId);
    }

    @GetMapping("/api/patients/{id}/visits")
    @PreAuthorize("#id.equals(authentication.principal) || hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<VisitResponse> getPatientVisits(@PathVariable Long id, @RequestParam(required = false) String visitStatus) {
        if (visitStatus == null) {
            return visitService.getPatientVisits(id);
        } else {
            return visitService.getPatientVisitsByStatus(id, visitStatus);
        }
    }

    @GetMapping("/api/employees/{id}/visits")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<VisitResponse> getEmployeeVisits(@PathVariable Long id,
                                                 @RequestParam(required = false) String visitStatus,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (visitStatus != null && date != null) {
            return visitService.getEmployeeVisitsByDateAndStatus(id, date, visitStatus);
        } else if (visitStatus != null) {
            return visitService.getEmployeeVisitsByStatus(id, visitStatus);
        } else if (date != null) {
            return visitService.getEmployeeVisitsByDate(id, date);
        } else {
            return visitService.getEmployeeVisits(id);
        }
    }

    @PostMapping("/api/patients/{id}/visits")
    @PreAuthorize("#id.equals(authentication.principal) || hasAnyRole('EMPLOYEE', 'ADMIN')")
    public VisitResponse createVisit(@PathVariable Long id, @RequestBody @Valid CreateVisitRequest request) {
        return visitService.createVisit(request);
    }

    @PutMapping("/api/patients/{id}/visits/{visitId}")
    @PreAuthorize("#id.equals(authentication.principal) || hasAnyRole('EMPLOYEE', 'ADMIN')")
    public VisitResponse editVisit(@PathVariable Long id, @PathVariable Long visitId, @RequestBody @Valid EditVisitRequest request) {
        return visitService.editVisitById(visitId, request);
    }
}

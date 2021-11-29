package com.project.orthodonticclinic.user.patient;

import com.project.orthodonticclinic.user.patient.payload.CreatePatientRequest;
import com.project.orthodonticclinic.user.patient.payload.PatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    @PreAuthorize("#id.equals(authentication.principal) || hasAnyRole('EMPLOYEE', 'ADMIN')")
    public PatientResponse getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<PatientResponse> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public PatientResponse createPatient(@RequestBody @Valid CreatePatientRequest request) {
        return patientService.createPatient(request);
    }
}

package com.project.orthodonticclinic.user.patient;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.user.account.AccountService;
import com.project.orthodonticclinic.user.account.RoleName;
import com.project.orthodonticclinic.user.patient.payload.CreatePatientRequest;
import com.project.orthodonticclinic.user.patient.payload.PatientResponse;
import com.project.orthodonticclinic.user.patient.util.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AccountService accountService;

    @Override
    public PatientResponse getPatientById(Long patientId) {
        var patient = getPatientEntityById(patientId);
        return patientMapper.mapToPatientResponse(patient);
    }

    private Patient getPatientEntityById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_PATIENT_ID));
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::mapToPatientResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientResponse createPatient(CreatePatientRequest request) {
        checkIfEmailIsUnique(request.getEmail());
        var account = accountService.createAccount(request.getUsername(), request.getPassword(), RoleName.PATIENT);
        var patient = patientMapper.mapFromCreatePatientRequest(request);
        patient.setAccount(account);
        patientRepository.save(patient);
        return patientMapper.mapToPatientResponse(patient);
    }

    private void checkIfEmailIsUnique(String email) {
        if (patientRepository.existsPatientByEmail(email)) {
            throw new ApplicationException(Error.EMAIL_IS_NOT_UNIQUE);
        }
    }
}

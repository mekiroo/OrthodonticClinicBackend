package com.project.orthodonticclinic.user.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Eliminate N+1 problem
    @Query("SELECT p FROM Patient p INNER JOIN FETCH p.account a INNER JOIN FETCH a.role")
    List<Patient> findAll();

    Boolean existsPatientByEmail(String email);

    Optional<Patient> findByAccountUsername(String username);
}

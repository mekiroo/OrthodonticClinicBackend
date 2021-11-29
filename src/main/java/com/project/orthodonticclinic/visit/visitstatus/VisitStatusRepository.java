package com.project.orthodonticclinic.visit.visitstatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitStatusRepository extends JpaRepository<VisitStatus, Long> {

    Optional<VisitStatus> findByName(EVisitStatusName name);
}

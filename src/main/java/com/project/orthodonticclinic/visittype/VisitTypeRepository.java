package com.project.orthodonticclinic.visittype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitTypeRepository extends JpaRepository<VisitType, Long> {

    // Eliminate N+1 problem
    @Query("SELECT v FROM VisitType v INNER JOIN FETCH v.position")
    List<VisitType> findAll();
}

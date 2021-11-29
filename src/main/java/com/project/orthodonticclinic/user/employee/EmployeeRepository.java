package com.project.orthodonticclinic.user.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Eliminate N+1 problem
    @Query("SELECT e FROM Employee e INNER JOIN FETCH e.account a INNER JOIN FETCH a.role INNER JOIN FETCH e.position")
    List<Employee> findAll();

    Optional<Employee> findByAccountUsername(String username);

    @Query("SELECT e FROM Employee e INNER JOIN FETCH e.account a INNER JOIN FETCH a.role INNER JOIN FETCH e.position " +
            "WHERE e.position.id = :positionId")
    List<Employee> findAllByPositionId(Long positionId);

    boolean existsEmployeeByEmail(String email);
}

package com.project.orthodonticclinic.user.employee;

import com.project.orthodonticclinic.user.employee.payload.CreateEmployeeRequest;
import com.project.orthodonticclinic.user.employee.payload.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse getEmployeeById(Long employeeId);

    List<EmployeeResponse> getAllEmployees();

    List<EmployeeResponse> getAllEmployeesByPositionId(Long positionId);

    EmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest);
}

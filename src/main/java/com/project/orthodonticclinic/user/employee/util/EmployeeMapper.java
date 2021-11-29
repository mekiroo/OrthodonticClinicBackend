package com.project.orthodonticclinic.user.employee.util;

import com.project.orthodonticclinic.user.employee.Employee;
import com.project.orthodonticclinic.user.employee.payload.CreateEmployeeRequest;
import com.project.orthodonticclinic.user.employee.payload.EmployeeResponse;

public interface EmployeeMapper {

    EmployeeResponse mapToEmployeeResponse(Employee employee);

    Employee mapFromCreateEmployeeRequest(CreateEmployeeRequest request);
}

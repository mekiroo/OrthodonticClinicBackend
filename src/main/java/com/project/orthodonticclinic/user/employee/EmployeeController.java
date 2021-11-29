package com.project.orthodonticclinic.user.employee;

import com.project.orthodonticclinic.user.employee.payload.CreateEmployeeRequest;
import com.project.orthodonticclinic.user.employee.payload.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping()
    public List<EmployeeResponse> getEmployees(@RequestParam(required = false) Long positionId) {
        if (positionId != null) {
            return employeeService.getAllEmployeesByPositionId(positionId);
        } else {
            return employeeService.getAllEmployees();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse createEmployee(@RequestBody @Valid CreateEmployeeRequest createEmployeeRequest) {
        return employeeService.createEmployee(createEmployeeRequest);
    }
}

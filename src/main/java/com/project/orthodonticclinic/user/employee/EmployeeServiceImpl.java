package com.project.orthodonticclinic.user.employee;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.position.PositionRepository;
import com.project.orthodonticclinic.user.account.AccountService;
import com.project.orthodonticclinic.user.account.RoleName;
import com.project.orthodonticclinic.user.employee.payload.CreateEmployeeRequest;
import com.project.orthodonticclinic.user.employee.payload.EmployeeResponse;
import com.project.orthodonticclinic.user.employee.util.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final EmployeeMapper employeeMapper;
    private final AccountService accountService;

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        var employee = getEmployeeEntityById(id);
        return employeeMapper.mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> getAllEmployeesByPositionId(Long id) {
        checkIfPositionExistsById(id);
        return employeeRepository.findAllByPositionId(id).stream()
                .map(employeeMapper::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    private void checkIfPositionExistsById(Long id) {
        if (!positionRepository.existsById(id)) {
            throw new ApplicationException(Error.INCORRECT_POSITION_ID);
        }
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        checkIfPositionExistsById(request.getPositionId());
        checkIfEmailIsUnique(request.getEmail());
        var account = accountService.createAccount(request.getUsername(), request.getPassword(), RoleName.EMPLOYEE);
        var employee = employeeMapper.mapFromCreateEmployeeRequest(request);
        employee.setAccount(account);
        employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeResponse(employee);
    }

    private void checkIfEmailIsUnique(String email) {
        if (employeeRepository.existsEmployeeByEmail(email)) {
            throw new ApplicationException(Error.EMAIL_IS_NOT_UNIQUE);
        }
    }

    private Employee getEmployeeEntityById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_EMPLOYEE_ID));
    }
}

package com.project.orthodonticclinic.user.employee.util;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.position.Position;
import com.project.orthodonticclinic.position.PositionRepository;
import com.project.orthodonticclinic.user.employee.Employee;
import com.project.orthodonticclinic.user.employee.payload.CreateEmployeeRequest;
import com.project.orthodonticclinic.user.employee.payload.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapperImpl implements EmployeeMapper {

    private final PositionRepository positionRepository;

    @Override
    public EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .positionId(employee.getPosition().getId())
                .positionName(employee.getPosition().getName())
                .build();
    }

    @Override
    public Employee mapFromCreateEmployeeRequest(CreateEmployeeRequest request) {
        var employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        var position = getPositionEntityById(request.getPositionId());
        employee.setPosition(position);
        return employee;
    }

    private Position getPositionEntityById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_POSITION_ID));
    }
}

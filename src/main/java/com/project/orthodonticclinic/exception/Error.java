package com.project.orthodonticclinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {
    // Authentication and authorization
    BAD_LOGIN_CREDENTIALS("Bad login credentials", HttpStatus.FORBIDDEN),
    INVALID_JWT("Invalid jwt", HttpStatus.FORBIDDEN),
    ACCESS_DENIED("Access denied", HttpStatus.FORBIDDEN),

    // Account
    USERNAME_IS_NOT_UNIQUE("Account with passed username already exists", HttpStatus.BAD_REQUEST),
    INCORRECT_ROLE_NAME("Role with passed name does not exist", HttpStatus.BAD_REQUEST),

    // User
    EMAIL_IS_NOT_UNIQUE("Account with passed email already exists", HttpStatus.BAD_REQUEST),

    // Patient
    INCORRECT_PATIENT_ID("Patient with passed id does not exist", HttpStatus.BAD_REQUEST),

    // Employee
    INCORRECT_EMPLOYEE_ID("Employee with passed id does not exist", HttpStatus.BAD_REQUEST),

    // Position
    INCORRECT_POSITION_ID("Position with passed id does not exist", HttpStatus.BAD_REQUEST),
    POSITION_NAME_IS_NOT_UNIQUE("Position with passed name already exists", HttpStatus.BAD_REQUEST),

    // Visit
    INCORRECT_VISIT_ID("Visit with passed id does not exist", HttpStatus.BAD_REQUEST),
    VISIT_CAN_NOT_BE_CREATED_INCORRECT_VISIT_STATUS("You can only create visit with status: WAITING", HttpStatus.BAD_REQUEST),
    VISIT_CAN_NOT_BE_CREATED_INCORRECT_EMPLOYEE_AND_VISIT_TYPE("Employee can not perform this visit type", HttpStatus.BAD_REQUEST),
    PATIENT_ALREADY_HAS_VISIT("Patient already has visit on passed date", HttpStatus.BAD_REQUEST),
    EMPLOYEE_ALREADY_HAS_VISIT("Employee already has visit on passed date", HttpStatus.BAD_REQUEST),

    // VisitStatus
    INCORRECT_VISIT_STATUS_ID("Visit status with passed id does not exist", HttpStatus.BAD_REQUEST),
    INCORRECT_VISIT_STATUS_NAME("Visit status with passed name does not exist", HttpStatus.BAD_REQUEST),

    // VisitType
    INCORRECT_VISIT_TYPE_ID("Visit type with passed id does not exist", HttpStatus.BAD_REQUEST),
    VISIT_TYPE_NAME_IS_NOT_UNIQUE("Visit type with passed name already exists", HttpStatus.BAD_REQUEST),

    // Date
    INVALID_DATE_FORMAT("Date is not in YYYY-MM-DD format", HttpStatus.BAD_REQUEST),
    START_DATE_IS_AFTER_END_DATE("Passed start date is after passed end date", HttpStatus.BAD_REQUEST),

    // Other
    INVALID_JSON_FORMAT("Passed request body is in incorrect format", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}

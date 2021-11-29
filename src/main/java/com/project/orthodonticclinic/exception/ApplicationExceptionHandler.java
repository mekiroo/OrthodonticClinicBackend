package com.project.orthodonticclinic.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleException(ApplicationException exception) {
        var httpStatus = exception.getError().getHttpStatus();
        var message = exception.getError().getMessage();
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
    }

    // Exception can be thrown during validating request constraints
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var bindingResult = ex.getBindingResult();
        var fieldErrors = bindingResult.getFieldErrors();
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldErrors.size(); i++) {
            stringBuilder.append(fieldErrors.get(i).getDefaultMessage());
            if (i != fieldErrors.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(stringBuilder.toString()));
    }

    // Exception can be thrown during parsing request body
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof DateTimeException) {
            return ResponseEntity
                    .status(Error.INVALID_DATE_FORMAT.getHttpStatus())
                    .body(new ErrorResponse(Error.INVALID_DATE_FORMAT.getMessage()));
        } else if ((cause instanceof JsonParseException) || (cause instanceof InvalidFormatException)) {
            return ResponseEntity.
                    status(Error.INVALID_JSON_FORMAT.getHttpStatus())
                    .body(new ErrorResponse(Error.INVALID_JSON_FORMAT.getMessage()));
        }
        System.out.println(cause);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

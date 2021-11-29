package com.project.orthodonticclinic.visit.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateVisitRequest {

    @NotNull(message = "patientId can not be null")
    private Long patientId;
    @NotNull(message = "employeeId can not be null")
    private Long employeeId;
    @NotNull(message = "visitTypeId can not be null")
    private Long visitTypeId;
    @NotBlank(message = "description can not be null or blank")
    private String description;
    @NotNull(message = "Date can not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @NotNull(message = "startTime can not be null")
    private LocalTime startTime;
}

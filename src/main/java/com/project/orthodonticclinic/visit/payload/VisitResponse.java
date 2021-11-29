package com.project.orthodonticclinic.visit.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitResponse {
    private Long id;
    private Long visitTypeId;
    private String visitTypeName;
    private String visitStatus;
    private String description;
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String date;
    private String startTime;
    private String endTime;
}

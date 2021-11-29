package com.project.orthodonticclinic.visit.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VisitTimeResponse {

    private String startTime;
    private String endTime;
}

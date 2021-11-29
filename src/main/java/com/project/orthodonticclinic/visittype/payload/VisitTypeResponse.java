package com.project.orthodonticclinic.visittype.payload;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class VisitTypeResponse {
    private Long id;
    private String name;
    private String description;
    private Long duration;
    private BigDecimal price;
    private boolean patientCanBook;
    private Long positionId;
}

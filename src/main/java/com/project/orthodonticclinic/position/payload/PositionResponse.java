package com.project.orthodonticclinic.position.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionResponse {
    private Long id;
    private String name;
}

package com.project.orthodonticclinic.visit.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TimeInterval {

    private LocalTime startTime;
    private LocalTime endTime;
}

package com.project.orthodonticclinic.visit;

import com.ocadotechnology.gembus.test.Arranger;
import com.project.orthodonticclinic.visit.payload.CreateVisitRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitTestHelper {

    public static CreateVisitRequest createCreateVisitRequest() {
        var request = new CreateVisitRequest();
        request.setPatientId(Arranger.someLong());
        request.setEmployeeId(Arranger.someLong());
        request.setVisitTypeId(Arranger.someLong());
        request.setDescription(Arranger.someString());
        request.setDate(LocalDate.now());
        request.setStartTime(LocalTime.now());
        return request;
    }
}

package com.project.orthodonticclinic.visit.util;

import com.project.orthodonticclinic.visit.Visit;
import com.project.orthodonticclinic.visit.payload.CreateVisitRequest;
import com.project.orthodonticclinic.visit.payload.VisitResponse;
import com.project.orthodonticclinic.visit.payload.VisitTimeResponse;

public interface VisitMapper {

    VisitResponse mapToVisitResponse(Visit visit);

    Visit mapFromCreateVisitRequest(CreateVisitRequest request);

    VisitTimeResponse mapFromTimeIntervalToVisitTimeResponse(TimeInterval timeInterval);
}
